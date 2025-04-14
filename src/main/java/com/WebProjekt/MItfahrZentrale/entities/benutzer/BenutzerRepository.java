package com.WebProjekt.MItfahrZentrale.entities.benutzer;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BenutzerRepository extends JpaRepository<Benutzer, Long> { 
    //extends → Für Vererbung zwischen Klassen oder Interfaces
    // implements → Für die Implementierung von Interfaces in Klassen

    Optional<Benutzer> findByEMail(String eMail); //Wird automatisch von JPA implementiert.
    
}
