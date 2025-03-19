package com.WebProjekt.MItfahrZentrale.ui.benutzer;

import java.util.Enumeration;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import com.WebProjekt.MItfahrZentrale.MItfahrZentraleApplication;
import com.WebProjekt.MItfahrZentrale.entities.benutzer.Benutzer;
import com.WebProjekt.MItfahrZentrale.services.benutzer.BenutzerServiceImpl;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


@Controller
@SessionAttributes("benutzerFormular") // listet Model-Attribute, die (transparent) in Session gespeichert werden sollen. Nutzung wie gewohnt per @ModelAttribute. 
public class BenutzerController { // Wird über mehrere Requests automatisch wieder ins Model geladen.

    private static final Logger logger = LoggerFactory.getLogger(BenutzerController.class); //mit .class erhalte ich den Typ des Objektes, nicht die Instanz
    //LoggerFactory müssen als Fabrikklasse nicht direkt instanziiert werden. 

    int maxWunsch = 5;

    @Autowired private BenutzerServiceImpl benutzerService;//DependencyInjection, Instanziierung und initialisierung
    //Autowired weist Spring an automatisch die passende Bean zur Verfügung zu stellen

    @ModelAttribute("benutzerFormular") //Wird vor jedem Handler aufruf aufgerufen. Initialisiert das Attribut, falls es nicht existiert.
    public BenutzerFormular initBenutzer(){ //Der Rückgabewert wird im Model und Session(@SessionAttributes) unter "benutzer" hinzugefügt.
        return new BenutzerFormular();
    }

    private void enumerateSession(HttpSession session){ 
        Enumeration<String> attributeNames = session.getAttributeNames();
        while(attributeNames.hasMoreElements()){ 
            String attributeName = attributeNames.nextElement();
            Object value = session.getAttribute(attributeName);
            logger.info(attributeName + (String) value);
        }
    }

    //Spring sucht @ModelAttribute Parameter der Reihe nach in Model, dann in @SessionAttributes, dann in Pfadvariablen, sonst erzeugt es eine neue Instanz.

    @PostMapping("/submit") //RequestHandler Methode
    public String postBenutzerDaten(@Valid @ModelAttribute("benutzerFormular") BenutzerFormular benutzerFormular, //BenutzerFormular befüllt benutzerFormular automatisch. Keine explizite Zuweisung nötig mit benutzerFormular.setName(name); man braucht das th_value in input feld, damit werte gezogen werden können
    //@Calid aktiviert die Validierungsüberprüfung im benutzerFormular
                                    BindingResult result,
                                    Locale locale,
                                    Model model){

        model.addAttribute("maxWunsch", maxWunsch);
        model.addAttribute("sprache", locale.getDisplayLanguage());

        if (result.hasErrors()){
            return "benutzerbearbeiten";
        }

        Benutzer benutzer = new Benutzer();
        benutzerFormular.toBenutzer(benutzer);

        if(benutzerFormular.getId() == 0){ //ID ist im Formualr gesetzt, wenn benutzer aus der Datenbank geholt wurde, also benutzer/n betätigt wurde
            try{ 
                benutzer = benutzerService.speichereBenutzer(benutzer);
            }catch(Exception e){ 
                model.addAttribute("info", "Fehler beim Speichern des Benutzers");
            }
            
        }else{ 
            benutzer = benutzerService.aktualisiereBenutzer(benutzer);
        }
        

        benutzerFormular.fromBenutzer(benutzer);
        model.addAttribute("benutzer", benutzer); //wichtig, damit das Frontend auf daten zugreifen kann
        model.addAttribute("benutzerID", benutzer.getId());
        benutzerFormular.passwort = "";

        return "redirect:/benutzer/" + benutzer.getId(); //nach dem post auf andern Pfad geleitet
        
        
    }

    @GetMapping("/submit") //brauchen wir nur für den Sprachen wechsel
    public String getSubmit(@RequestParam(name = "sprache", required = false) String sprache, //Parameter sprache ist optional, wenn er fehlt, wird er auf null gesetzt
                            Locale locale,
                            Model model) { //Model wird per DepedencyInjection initialisiert
        model.addAttribute("maxWunsch", maxWunsch);
        model.addAttribute("sprache", locale.getDisplayLanguage());
        
        return "benutzerbearbeiten";
    }

    @GetMapping("/benutzer/0")
    public String showForm(@ModelAttribute("benutzerFormular") BenutzerFormular benutzerFormular,
                            @ModelAttribute("benutzer") Benutzer benutzer,
                            Locale locale,
                            Model model){ 
    
    benutzerFormular = new BenutzerFormular();
    benutzer = new Benutzer();
    model.addAttribute("benutzerFormular", benutzerFormular);
    model.addAttribute("titel", "Neues Benutzerprofil");

    return "benutzerbearbeiten";

    }

    @GetMapping("/benutzer/{n}") //RequestHandler Methode
    public String getBenutzerID(@ModelAttribute("benutzerFormular") BenutzerFormular benutzerFormular,
                                @ModelAttribute("benutzer") Benutzer benutzer,
                                @PathVariable int n,
                                Locale locale,
                                Model model) {

        try{ 
            Benutzer aktBenutzer = benutzerService.holeBenutzerMitId(n).orElseThrow(() -> new RuntimeException("Benutzer nicht gefunden"));
            benutzerFormular.fromBenutzer(aktBenutzer);
        }catch(Exception e){ 
            model.addAttribute("info", e);
            logger.error(e.toString());
            benutzerFormular = new BenutzerFormular();
            model.addAttribute("benutzerFormular", benutzerFormular);
        }
        
        benutzerFormular.setId(n);
        benutzerFormular.setId(n);
        benutzerFormular.passwort = "";
        model.addAttribute("benutzerFormular", benutzerFormular);

        model.addAttribute("benutzerID", n);
        model.addAttribute("maxWunsch", maxWunsch);
        model.addAttribute("sprache", locale.getDisplayLanguage());
        return "benutzerbearbeiten";
    }

    // @GetMapping("/benutzer/{n}") //RequestHandler Methode
    // public String getBenutzerID(@ModelAttribute("benutzerFormular") BenutzerFormular benutzerFormular,
    //                             @PathVariable int n,
    //                             Locale locale,
    //                             HttpSession session,
    //                             Model model) {
        
    //     if (n == 0){
    //         session.setAttribute("benutzerFormular", new BenutzerFormular());
    //         session.setAttribute("benutzer", new Benutzer());
    //         model.addAttribute("titel", "Neues Benutzerprofil");
    //         session.setAttribute("benutzerID", n);
    //         return "benutzerbearbeiten";

    //     }else if(n > 0){
    //         Benutzer aktBenutzer = benutzerService.holeBenutzerMitId(n).orElseThrow(() -> new RuntimeException("Benutzer nicht gefunden"));
    //         benutzerFormular.fromBenutzer(aktBenutzer);
    //         session.setAttribute("benutzerFormular", benutzerFormular);
    //         session.setAttribute("benutzerID", n);
    //         benutzerFormular.passwort = "";
    //         model.addAttribute("benutzerFormular", benutzerFormular);

    //         model.addAttribute("benutzerID", n);
    //         model.addAttribute("maxWunsch", maxWunsch);
    //         model.addAttribute("sprache", locale.getDisplayLanguage());
    //         return "benutzerbearbeiten";
    //     }else{

    //         model.addAttribute("benutzerID", n);
    //         model.addAttribute("maxWunsch", maxWunsch);
    //         model.addAttribute("sprache", locale.getDisplayLanguage());
    //         return "benutzerbearbeiten";

    //     }
    // }   
}
