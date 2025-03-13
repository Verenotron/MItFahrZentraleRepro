package com.WebProjekt.MItfahrZentrale.ui.benutzer;

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

    @ModelAttribute("benutzer")
    public BenutzerFormular initBenutzer(){
        return new BenutzerFormular();
    }

    // @GetMapping("/benutzer")
    // public String getBenutzer() {
    //     return "benutzerbearbeiten";
    // }

    @PostMapping("/submit")
    public String postBenutzerDaten(@ModelAttribute("benutzer") BenutzerFormular benutzerFormular //befüllt benutzerFormular automatisch. Keine explizite zuweisung nötig mit benutzerFormular.setName(name); man braucht das th_value in input feld, damit werte gezogen werden können
                                    ){
        return "benutzerbearbeiten";
    }

    @GetMapping("/benutzer/{n}")
    public String getBenutzerID(@PathVariable int n,
                                Model model) {
        model.addAttribute("benutzerID", n);
        return "benutzerbearbeiten";
    }
    
    
}
