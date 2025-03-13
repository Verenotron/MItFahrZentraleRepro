package com.WebProjekt.MItfahrZentrale.ui.benutzer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BenutzerFormular {
    String name;
    String eMail;
    String passwort;
    String geburtstag;
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
    public String getGeburtstag() {
        return geburtstag;
    }
    public void setGeburtstag(String geburtstag) {
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
