package com.WebProjekt.MItfahrZentrale.ui;

import java.security.Principal;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice { //Hier kann man global für jeden controller im Vorfeld einstellungen für das Model vornehmen.

    @ModelAttribute("username")
    public String username(Principal principal){
        return principal != null ? principal.getName() : null;
    }
    
}
