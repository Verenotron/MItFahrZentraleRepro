package com.WebProjekt.MItfahrZentrale.ui.benutzer;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import com.WebProjekt.MItfahrZentrale.validators.Passwort;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

public class BenutzerFormular {
    @NotBlank
    @Size(min=3, max=80, message="Name muss min. 3 und max. 80 Zeichen lang sein.") //ohne message gibt es Default-Fehlermeldungen
    String name;
    
    @Size(min=5, max=80, message="EMail muss zwischen 5 und 80 Zeichen lang sein.")
    @Email(message="Es mus ich um eine Email handeln.")
    String eMail;

    @Passwort(value = "17", value2 = "siebzehn")
    String passwort;
    
    @NotNull(message="Geburtstag muss existieren.")
    @Past(message="Geburtstag muss in der Vergangenheit liegen.")
    @DateTimeFormat(pattern="yyyy-MM-dd") // so kommt's vom HTML-date-Inputfeld herein
    LocalDate geburtstag;
    
    Set<String> mag = new HashSet<String>();
    Set<String> magNicht = new HashSet<String>();

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String geteMail() {
        return eMail;
    }
    public void seteMail(String eMail) {
        this.eMail = eMail;
    }
    public String getPasswort() {
        return passwort;
    }
    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }
    public LocalDate getGeburtstag() {
        return geburtstag;
    }
    public void setGeburtstag(LocalDate geburtstag) {
        this.geburtstag = geburtstag;
    }
    public Set<String> getMag() {
        return mag;
    }
    public void setMag(String element) {
        this.mag.add(element);
    }
    public Set<String> getMagNicht() {
        return magNicht;
    }
    public void setMagNicht(String element) {
        this.magNicht.add(element);
    }


}
