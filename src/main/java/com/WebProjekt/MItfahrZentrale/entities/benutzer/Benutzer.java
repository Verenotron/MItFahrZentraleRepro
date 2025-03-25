package com.WebProjekt.MItfahrZentrale.entities.benutzer;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import com.WebProjekt.MItfahrZentrale.entities.tour.Tour;
import com.WebProjekt.MItfahrZentrale.validators.Passwort;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Version;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

@Entity
public class Benutzer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Version
    private long version;

    @NotBlank(message = "{fehler.benutzer.name.notnull}")
    @Size(min=3, max=80, message="{fehler.benutzer.name.size}")
    private String name;
    
    @Size(min=5, max=80, message="{fehler.benutzer.email.size}")
    @Email(message="{fehler.benutzer.email.emailformat}")
    private String eMail;

    @Passwort(value = "17", value2 = "siebzehn", message="{gutespasswort.fehler}")
    private String passwort;
    
    @NotNull(message="Geburtstag muss existieren.")
    @Past(message="Geburtstag muss in der Vergangenheit liegen.")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate geburtstag;
    
    @ElementCollection
    private Set<String> mag = new HashSet<String>();

    @ElementCollection
    private Set<String> magNicht = new HashSet<String>();

    @OneToMany(mappedBy="anbieter", cascade = CascadeType.REMOVE) //Wenn der Benutzer gelöscht wird, werden auh seine angebotenen Touren gelöscht
    private List<Tour> angeboteneTouren = new LinkedList<Tour>();

    public String getName() {
        return name;
    }
    public List<Tour> getAngeboteneTouren() {
        return angeboteneTouren;
    }
    public void addTouren(Tour neueTour) {
        this.angeboteneTouren.add(neueTour);
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
    public void setMag(Set<String> mag) {
        this.mag = mag;
    }
    public Set<String> getMagNicht() {
        return magNicht;
    }
    public void setMagNicht(Set<String> magNicht) {
        this.magNicht = magNicht;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public long getVersion() {
        return version;
    }
    public void setVersion(long version) {
        this.version = version;
    }

    
    
}
