package com.WebProjekt.MItfahrZentrale.ui.benutzer;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import com.WebProjekt.MItfahrZentrale.entities.benutzer.Benutzer;
import com.WebProjekt.MItfahrZentrale.validators.Passwort;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

public class BenutzerFormular {

    long id;
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    @NotBlank(message = "{fehler.benutzer.name.notnull}")
    @Size(min=3, max=80, message="{fehler.benutzer.name.size}") //ohne message gibt es Default-Fehlermeldungen
    String name;
    
    @Size(min=5, max=80, message="{fehler.benutzer.email.size}")
    @Email(message="{fehler.benutzer.email.emailformat}")
    String eMail;

    //@NotBlank(message="{benutzer.passwort.ungesetzt}") //Passwort wird nur im Backend überprüft ob es existiert. aber nicht hier
    @Passwort(value = "17", value2 = "siebzehn", message="{gutespasswort.fehler}")
    String passwort;
    
    @NotNull(message="Geburtstag muss existieren.")
    @Past(message="Geburtstag muss in der Vergangenheit liegen.")
    @DateTimeFormat(pattern="yyyy-MM-dd") // so kommt's vom HTML-date-Inputfeld herein
    LocalDate geburtstag;
    
    Set<String> mag = new HashSet<String>();
    Set<String> magNicht = new HashSet<String>();

    public void toBenutzer(Benutzer b){ // befuellt b mit Formularinhalt
        b.setId(this.getId());
        b.setName(this.name);
        b.seteMail(this.eMail);
        b.setGeburtstag(this.geburtstag);
        b.setPasswort(this.passwort); //muss besonders behandelt werden
        b.setMag(this.mag);
        b.setMagNicht(this.magNicht);
    } 

    public void fromBenutzer(Benutzer b){ // fuellt Formularinhalt aus b
        this.id = b.getId();
        this.name = b.getName();
        this.eMail = b.geteMail();
        this.geburtstag = b.getGeburtstag();
        this.passwort = b.getPasswort(); //muss besonders behandelt werden
        this.mag = b.getMag();
        this.magNicht = b.getMagNicht();
    } 

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
