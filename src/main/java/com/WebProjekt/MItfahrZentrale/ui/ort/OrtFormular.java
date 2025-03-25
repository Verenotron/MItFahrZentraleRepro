package com.WebProjekt.MItfahrZentrale.ui.ort;

import com.WebProjekt.MItfahrZentrale.entities.ort.Ort;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;

public class OrtFormular {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long version;

    @NotEmpty // oder notBlank, falls Leerzeichen ignotriert werden sollen.
    private String name;
    
    private double geobreite;
    private double geolaenge;

    public void toOrt(Ort o){
        o.setId(this.getId());
        o.setName(this.getName());
        o.setGeobreite(this.geobreite);
        o.setGeolaenge(this.geolaenge);
    }

    public void fromOrt(Ort o){
        this.id = o.getId();
        this.name = o.getName();
        this.geobreite = o.getGeobreite();
        this.geolaenge = o.getGeolaenge();
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
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getGeobreite() {
        return geobreite;
    }
    public void setGeobreite(double geobreite) {
        this.geobreite = geobreite;
    }
    public double getGeolaenge() {
        return geolaenge;
    }
    public void setGeolaenge(double geolaenge) {
        this.geolaenge = geolaenge;
    }
    
    
}
