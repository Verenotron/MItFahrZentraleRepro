package com.WebProjekt.MItfahrZentrale.entities.ort;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotNull;

@Entity
public class Ort {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Version
    private long version;

    @NotNull
    private String name;
    private double grobreite;

    
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getGrobreite() {
        return grobreite;
    }
    public void setGrobreite(double grobreite) {
        this.grobreite = grobreite;
    }
    public double getGeolaenge() {
        return geolaenge;
    }
    public void setGeolaenge(double geolaenge) {
        this.geolaenge = geolaenge;
    }
    private double geolaenge;

    
    
}
