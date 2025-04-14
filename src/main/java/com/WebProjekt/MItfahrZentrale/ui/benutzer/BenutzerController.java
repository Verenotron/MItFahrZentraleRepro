package com.WebProjekt.MItfahrZentrale.ui.benutzer;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.WebProjekt.MItfahrZentrale.entities.benutzer.Benutzer;
import com.WebProjekt.MItfahrZentrale.services.benutzer.BenutzerServiceImpl;

import jakarta.validation.Valid;


@Controller
@SessionAttributes("benutzerFormular") // listet Model-Attribute, die (transparent) in Session gespeichert werden sollen. Nutzung wie gewohnt per @ModelAttribute. 
@RequestMapping("/admin")
public class BenutzerController {// Wird über mehrere Requests automatisch wieder ins Model geladen.

    private static final Logger logger = LoggerFactory.getLogger(BenutzerController.class); //mit .class erhalte ich den Typ des Objektes, nicht die Instanz
    //LoggerFactory müssen als Fabrikklasse nicht direkt instanziiert werden. 

    int maxWunsch = 5;

    @Autowired private BenutzerServiceImpl benutzerService;//DependencyInjection, Instanziierung und initialisierung
    //Autowired weist Spring an automatisch die passende Bean zur Verfügung zu stellen
    @Autowired PasswordEncoder passwordEncoder;

    @ModelAttribute("benutzerFormular") //Wird vor jedem Handler aufruf aufgerufen. Initialisiert das Attribut, falls es nicht existiert.
    public BenutzerFormular initBenutzer(){ //Der Rückgabewert wird im Model und Session(@SessionAttributes) unter "benutzer" hinzugefügt.
        return new BenutzerFormular();
    }

    //Spring sucht @ModelAttribute Parameter der Reihe nach in Model, dann in @SessionAttributes, dann in Pfadvariablen, sonst erzeugt es eine neue Instanz.
    //@GetMapping wird aufgerufen, wenn die Seite per URL-Aufruf oder Link geladen wird.
    //@PostMapping wird aufgerufen, wenn ein Formular per Submit-Button abgeschickt wird.

    @PostMapping("/submit") //RequestHandler Methode, URL pfad wird beim Post in hintergrund übertragen im HTTP-Body, nicht in der URL
    public String postBenutzerDaten(@Valid @ModelAttribute("benutzerFormular") BenutzerFormular benutzerFormular, //Spring befüllt benutzerFormular mit daten aus der View automatisch. Keine explizite Zuweisung nötig mit benutzerFormular.setName(name); man braucht das th_value in input feld, damit werte gezogen werden können
    //@Calid aktiviert die Validierungsüberprüfung im benutzerFormular
                                    BindingResult result,
                                    //Locale locale,
                                    Model model){

        model.addAttribute("maxWunsch", maxWunsch);
        //model.addAttribute("sprache", locale.getDisplayLanguage());

        Benutzer benutzer = new Benutzer();
        benutzerFormular.setPasswort(passwordEncoder.encode(benutzerFormular.getPasswort()));//Passwort wirv verschlüsselt
        benutzerFormular.toBenutzer(benutzer);

        if(benutzerFormular.getId() == 0 && benutzerFormular.getPasswort().isEmpty()){ 
            result.rejectValue(
            "passwort", // Formularfeld
            "benutzer.passwort.ungesetzt", // Message-Key
            "Passwort wurde noch nicht gesetzt");
        }else if(benutzerFormular.getId() == 0){ //ID ist im Formualr gesetzt, wenn benutzer aus der Datenbank geholt wurde, also benutzer/n betätigt wurde
            try{ 
                benutzer = benutzerService.speichereBenutzer(benutzer);
            }catch(Exception e){ 
                model.addAttribute("info", "Fehler beim Speichern des Benutzers");
            }
            
        }else{
            benutzer = benutzerService.aktualisiereBenutzer(benutzer);
        }

        if (result.hasErrors()){ //falls result.reject aufgerufen ist das hier true
            benutzerFormular.setPasswort("");
            return "benutzerbearbeiten";
        }
        
        benutzerFormular.fromBenutzer(benutzer);
        model.addAttribute("benutzerID", benutzer.getId());
        benutzerFormular.passwort = "";

        return "redirect:/admin/benutzer/" + benutzer.getId(); //nach dem post auf andern Pfad geleitet
        
        
    }

    @GetMapping("/submit") //brauchen wir nur für den Sprachen wechsel, wenn ich erst auf submit und dann die sprache wechseln will
    public String getSubmit(@RequestParam(name = "sprache", required = false) String sprache, //Parameter sprache ist optional, wenn er fehlt, wird er auf null gesetzt
                            Locale locale,
                            Model model) { //Model wird per DepedencyInjection initialisiert
        model.addAttribute("maxWunsch", maxWunsch);
        model.addAttribute("sprache", locale.getDisplayLanguage());
        
        return "benutzerbearbeiten";
    }

    @GetMapping("/benutzer")
    public String showBenutzer(Model model){ 

        try{ 
            List<Benutzer> alleBenutzer = benutzerService.holeAlleBenutzer();
            alleBenutzer.sort(Comparator.comparing(Benutzer::getName).thenComparing(Benutzer::geteMail)); //erst nach name, dann nach email sortieren
            model.addAttribute("benutzerListe", alleBenutzer);
        }catch(Exception e){ 
            model.addAttribute("info", e.toString());
            logger.info("Fehler beim Laden der Benutzer aus der Datenbank.");
        }

        return "benutzerliste";
    }

    @GetMapping("/benutzer/{n}/del")
    public String loescheBenutzer(@PathVariable int n){ 
        benutzerService.loescheBenutzerMitId(n);
        return "redirect:/admin/benutzer";
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
    public String getBenutzerID(@ModelAttribute("benutzerFormular") BenutzerFormular benutzerFormular, //benutzerFormular als Modelattribut hinzugrfügt. Muss nicht mehr manuell gemacht werden die z.B. maxwunsch
                                @ModelAttribute("benutzer") Benutzer benutzer,
                                @PathVariable("n") int n,
                                Locale locale,
                                Model model) {

        try{ 
            Benutzer aktBenutzer = benutzerService.holeBenutzerMitId(n).orElseThrow(() -> new RuntimeException("Benutzer nicht gefunden"));//Lambda Ausdruck innerhalb einer orElseThrow Methode, wird geworfen wenn kein Wert im Optional vorhanden ist
            benutzerFormular.fromBenutzer(aktBenutzer);
        }catch(Exception e){ 
            model.addAttribute("info", e);
            logger.error(e.toString());
            benutzerFormular = new BenutzerFormular();
            model.addAttribute("benutzerFormular", benutzerFormular);
        }
        
        benutzerFormular.passwort = "";
        model.addAttribute("benutzerID", n);
        model.addAttribute("maxWunsch", maxWunsch);
        model.addAttribute("sprache", locale.getDisplayLanguage());
        return "benutzerbearbeiten";
    }

    @GetMapping("/hx/{benutzerId}/{feldName}")
    public String feldBearbeiten(@PathVariable("benutzerId") String id,
                                @PathVariable("feldName") String fieldName,
                                @ModelAttribute("benutzerFormular") BenutzerFormular benutzerFormular,
                                Model model){

        Benutzer benutzer = benutzerService.holeBenutzerMitId(Integer.parseInt(id)).orElseThrow(() -> new RuntimeException("Benutzer nicht gefunden"));
        benutzerFormular.fromBenutzer(benutzer);
        model.addAttribute("benutzerFormular", benutzerFormular);
        
        model.addAttribute("benutzerid", id);
        model.addAttribute("feldname", fieldName);
        if(fieldName.equals("namensfeld")){
            model.addAttribute("wert", benutzerFormular.getName());
        }else if(fieldName.equals("emailfeld")){
            model.addAttribute("wert", benutzerFormular.geteMail());
        }else{
            logger.error("HTMx Problem: Es koennen nur die Felder email und name durch HTMX verarbeitet werden.");
        }
        

        return "fragments/benutzer-zeile :: feldbearbeiten";
    }

    @PutMapping("/hx/{benutzerId}/{feldName}")
    public String feldAendern(@PathVariable("benutzerId") String id, //als ich die ID hier unten im model gespeicht habe statt die aus dem BenutzerFormular hats plötzlich funktioniert...
                            @PathVariable("feldName") String feldName,
                            @ModelAttribute("benutzerFormular") BenutzerFormular benutzerFormular,
                            @RequestParam("wert") String wert,
                            Model model){

        Benutzer benutzer = new Benutzer();
        benutzerFormular.toBenutzer(benutzer);
        
        switch(feldName){
            case "namensfeld":
            benutzerFormular.setName(wert);
            break;
            case "emailfeld":
            benutzerFormular.seteMail(wert);
        }
        try{
            benutzerFormular.toBenutzer(benutzer);
            benutzerService.aktualisiereBenutzer(benutzer);
            benutzerFormular.fromBenutzer(benutzer);
        }catch(Exception e){

            benutzer = benutzerService.holeBenutzerMitId(Integer.parseInt(id)).orElseThrow(() -> new RuntimeException("Benutzer nicht gefunden"));
            benutzerFormular.fromBenutzer(benutzer);
            switch(feldName){
                case "namensfeld":
                    wert = benutzerFormular.getName();
                break;
                case "emailfeld":
                    wert = benutzerFormular.geteMail();
                break;
            }

            model.addAttribute("benutzerFormular", benutzerFormular);
            model.addAttribute("feldname", feldName);
            model.addAttribute("benutzerid", id);
            model.addAttribute("benutzer", benutzer);
            model.addAttribute("wert", wert);

            model.addAttribute("info", e.getMessage());

            return "fragments/benutzer-zeile :: feldbearbeiten";
        }
        

        model.addAttribute("benutzerFormular", benutzerFormular);
        model.addAttribute("benutzerid", id);
        model.addAttribute("benutzer", benutzer);
        model.addAttribute("wert", wert);
        model.addAttribute("feldname", feldName);

        return "fragments/benutzer-zeile :: feldausgeben";
    }
}
