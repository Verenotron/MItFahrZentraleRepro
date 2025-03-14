package com.WebProjekt.MItfahrZentrale.ui.benutzer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import jakarta.validation.Valid;



@Controller
@SessionAttributes("benutzer")
public class BenutzerController {

    private static final Logger logger = LoggerFactory.getLogger(BenutzerController.class); //mit .class erhalte ich den Typ des Objektes, nicht die Instanz
    //LoggerFactory müssen als Fabrikklasse nicht direkt instanziiert werden. 

    int maxWunsch = 5;

    @ModelAttribute("benutzer")
    public BenutzerFormular initBenutzer(){
        return new BenutzerFormular();
    }

    // @GetMapping("/benutzer")
    // public String getBenutzer() {
    //     return "benutzerbearbeiten";
    // }

    @PostMapping("/submit") //RequestHandler Methode
    public String postBenutzerDaten(@Valid @ModelAttribute("benutzer") BenutzerFormular benutzerFormular, //BenutzerFormular befüllt benutzerFormular automatisch. Keine explizite Zuweisung nötig mit benutzerFormular.setName(name); man braucht das th_value in input feld, damit werte gezogen werden können
    //@Calid aktiviert die Validierungsüberprüfung im benutzerFormular
                                    BindingResult result,
                                    Model model){
        model.addAttribute("maxWunsch", maxWunsch);
        if (result.hasErrors()){
            return "benutzerbearbeiten";
        }
        return "benutzerbearbeiten";
        
    }

    @GetMapping("/benutzer/{n}") //RequestHandler Methode
    public String getBenutzerID(@PathVariable int n,
                                Model model) {
        model.addAttribute("benutzerID", n);
        model.addAttribute("maxWunsch", maxWunsch);
        return "benutzerbearbeiten";
    }
    
    
}
