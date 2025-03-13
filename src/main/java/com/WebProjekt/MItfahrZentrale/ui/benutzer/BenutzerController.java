package com.WebProjekt.MItfahrZentrale.ui.benutzer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;



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

    @PostMapping("/submit")
    public String postBenutzerDaten(@ModelAttribute("benutzer") BenutzerFormular benutzerFormular, //befüllt benutzerFormular automatisch. Keine explizite Zuweisung nötig mit benutzerFormular.setName(name); man braucht das th_value in input feld, damit werte gezogen werden können
                                    Model model){
        model.addAttribute("maxWunsch", maxWunsch);
        return "benutzerbearbeiten";
    }

    @GetMapping("/benutzer/{n}")
    public String getBenutzerID(@PathVariable int n,
                                Model model) {
        model.addAttribute("benutzerID", n);
        model.addAttribute("maxWunsch", maxWunsch);
        return "benutzerbearbeiten";
    }
    
    
}
