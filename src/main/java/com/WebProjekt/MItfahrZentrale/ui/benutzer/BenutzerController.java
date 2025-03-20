package com.WebProjekt.MItfahrZentrale.ui.benutzer;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.web.Link;
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

import jakarta.validation.Valid;


@Controller
@SessionAttributes("benutzerFormular") // listet Model-Attribute, die (transparent) in Session gespeichert werden sollen. Nutzung wie gewohnt per @ModelAttribute. 
public class BenutzerController {// Wird über mehrere Requests automatisch wieder ins Model geladen.

    private static final Logger logger = LoggerFactory.getLogger(BenutzerController.class); //mit .class erhalte ich den Typ des Objektes, nicht die Instanz
    //LoggerFactory müssen als Fabrikklasse nicht direkt instanziiert werden. 

    int maxWunsch = 5;

    @Autowired private BenutzerServiceImpl benutzerService;//DependencyInjection, Instanziierung und initialisierung
    //Autowired weist Spring an automatisch die passende Bean zur Verfügung zu stellen

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
        model.addAttribute("benutzerID", benutzer.getId());
        benutzerFormular.passwort = "";

        return "redirect:/benutzer/" + benutzer.getId(); //nach dem post auf andern Pfad geleitet
        
        
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
        return "redirect:/benutzer";
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
                                @PathVariable int n,
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

}
