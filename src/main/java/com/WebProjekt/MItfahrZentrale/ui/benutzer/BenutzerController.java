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
@SessionAttributes("benutzerFormular") //Sorgt dasfür, das bereits erstellete Objekt(initBneutzer) unter "benutzer" über mehrere Requests hinweg, in der Session gespeichert bleibt
public class BenutzerController {

    private static final Logger logger = LoggerFactory.getLogger(BenutzerController.class); //mit .class erhalte ich den Typ des Objektes, nicht die Instanz
    //LoggerFactory müssen als Fabrikklasse nicht direkt instanziiert werden. 

    int maxWunsch = 5;

    @Autowired private BenutzerServiceImpl benutzerService;//DependencyInjection, 
    //Autowired weist Spring an automatisch die passende Bean zur Verfügung zu stellen

    @ModelAttribute("benutzerFormular")
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

    @PostMapping("/submit") //RequestHandler Methode
    public String postBenutzerDaten(@Valid @ModelAttribute("benutzerFormular") BenutzerFormular benutzerFormular, //BenutzerFormular befüllt benutzerFormular automatisch. Keine explizite Zuweisung nötig mit benutzerFormular.setName(name); man braucht das th_value in input feld, damit werte gezogen werden können
    //@Calid aktiviert die Validierungsüberprüfung im benutzerFormular
                                    BindingResult result,
                                    Locale locale,
                                    HttpSession session,
                                    Model model){

        model.addAttribute("maxWunsch", maxWunsch);
        model.addAttribute("sprache", locale.getDisplayLanguage());

        if (result.hasErrors()){
            return "benutzerbearbeiten";
        }

        Benutzer benutzer = new Benutzer();
        benutzerFormular.toBenutzer(benutzer);

        if(benutzerFormular.getId() == 0){ //ID ist im Formualr gesetzt, wenn benutzer aus der Datenbank geholt wurde, also benutzer/n betätigt wurde
            benutzer = benutzerService.speichereBenutzer(benutzer);
        }else{ 
            benutzer = benutzerService.aktualisiereBenutzer(benutzer);
        }
        

        benutzerFormular.fromBenutzer(benutzer);
        model.addAttribute("benutzer", benutzer); //wichtig, damit das Frontend auf daten zugreifen kann
        model.addAttribute("benutzerID", benutzer.getId());
        session.setAttribute("benutzer", benutzer);
        benutzerFormular.passwort = "";

        return "benutzerbearbeiten";
        
        
    }

    @GetMapping("/submit") //brauchen wir nur für den Sprachen wechsel
    public String getSubmit(@RequestParam(name = "sprache", required = false) String sprache,
                            Locale locale,
                            Model model) {
        model.addAttribute("maxWunsch", maxWunsch);
        model.addAttribute("sprache", locale.getDisplayLanguage());
        
        return "benutzerbearbeiten";
    }

    @GetMapping("/benutzer/0")
    public String showForm(@ModelAttribute("benutzerFormular") BenutzerFormular benutzerFormular,
                            @ModelAttribute("benutzer") Benutzer benutzer,
                            Locale locale,
                            HttpSession session,
                            Model model){ 
    
    benutzerFormular = new BenutzerFormular();
    benutzer = new Benutzer();
    model.addAttribute("benutzerFormular", benutzerFormular);
    model.addAttribute("titel", "Neues Benutzerprofil");

    // session.setAttribute("benutzerFormular", new BenutzerFormular());
    // session.setAttribute("benutzer", new Benutzer());
    // model.addAttribute("titel", "Neues Benutzerprofil");
    // session.setAttribute("benutzerID", 0);
    return "benutzerbearbeiten";


    }

    @GetMapping("/benutzer/{n}") //RequestHandler Methode
    public String getBenutzerID(@ModelAttribute("benutzerFormular") BenutzerFormular benutzerFormular,
                                @ModelAttribute("benutzer") Benutzer benutzer,
                                @PathVariable int n,
                                Locale locale,
                                HttpSession session,
                                Model model) {

        Benutzer aktBenutzer = benutzerService.holeBenutzerMitId(n).orElseThrow(() -> new RuntimeException("Benutzer nicht gefunden"));
        benutzerFormular.fromBenutzer(aktBenutzer);
        
        benutzerFormular.setId(n);
        benutzerFormular.setId(n);
        session.setAttribute("benutzerFormular", benutzerFormular);
        //session.setAttribute("benutzerID", n);
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
