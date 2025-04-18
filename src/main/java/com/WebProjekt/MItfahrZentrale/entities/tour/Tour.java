package com.WebProjekt.MItfahrZentrale.entities.tour;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.WebProjekt.MItfahrZentrale.entities.benutzer.Benutzer;
import com.WebProjekt.MItfahrZentrale.entities.ort.Ort;
import com.WebProjekt.MItfahrZentrale.services.geo.GeoDistanz;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import jakarta.persistence.Version;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

@Entity
public class Tour {
    
    @Transient
    GeoDistanz geoDistanz;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Version
    private long version;

    private LocalDateTime abfahrDateTime;

    @PositiveOrZero
    private int preis;

    @Min(1)
    private int plaetze;

    private int buchungen;
    
    @Size(max=400)
    private String info;

    @ManyToOne
    private Benutzer anbieter;

    @NotNull
    @ManyToOne
    private Ort startOrt;

    @NotNull
    @ManyToOne
    private Ort zielOrt;

    @ManyToMany//Macht Probleme, wenn das da nicht steht. 
    private List<Benutzer> mitFahrgaeste;


    public boolean buche(Benutzer benutzer){
        if(buchungen == plaetze){
            return false;
        }else{
            buchungen += 1;
            this.mitFahrgaeste.add(benutzer);
            return true;
        }
    }
    public Ort getZielOrt() {
        return zielOrt;
    }
    public long getZielOrtId(){
        return this.zielOrt.getId();
    }
    public long getStartOrtId(){
        return this.startOrt.getId();
    }
    public String getStartOrtName(){
        return this.startOrt.getName();
    }
    public String getZielOrtName(){
        return this.zielOrt.getName();
    }
    public String getAnbieterName(){
        return this.anbieter.getName();
    }
    public long getAnbieterId(){
        return this.anbieter.getId();
    }
    // public List<Benutzer> getMitfahrGaeste(){
    //     return this.mitFahrgaeste;
    // }

    public double getDistanz(){
        return GeoDistanz.calculateDistance(startOrt.getGeobreite(), startOrt.getGeolaenge(), zielOrt.getGeobreite(), zielOrt.getGeolaenge());
    }

    public void setZielOrt(Ort zielOrt) {
        this.zielOrt = zielOrt;
    }
    public Ort getStartOrt() {
        return startOrt;
    }
    public void setStartOrt(Ort startOrt) {
        this.startOrt = startOrt;
    }
    public Benutzer getAnbieter() {
        return anbieter;
    }
    public void setAnbieter(Benutzer anbieter) {
        this.anbieter = anbieter;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public LocalDateTime getAbfahrDateTime() {
        return abfahrDateTime;
    }
    public void setAbfahrDateTime(LocalDateTime abfahrDateTime) {
        this.abfahrDateTime = abfahrDateTime;
    }
    public int getPreis() {
        return preis;
    }
    public void setPreis(int preis) {
        this.preis = preis;
    }
    public int getPlaetze() {
        return plaetze;
    }
    public void setPlaetze(int plaetze) {
        this.plaetze = plaetze;
    }
    public String getInfo() {
        return info;
    }
    public void setInfo(String info) {
        this.info = info;
    }
    public int getBuchungen() {
        return buchungen;
    }
    public void setBuchungen(int buchungen) {
        this.buchungen = buchungen;
    }
    public List<Benutzer> getMitFahrgaeste() {
        return mitFahrgaeste;
    }
    public void setMitFahrgaeste(List<Benutzer> mitFahrgaeste) {
        this.mitFahrgaeste = mitFahrgaeste;
    }
    public List<String> getMitFahrGastNamen(){
        List<String> namen = new ArrayList<>();
        for(Benutzer benutzer: mitFahrgaeste){
            namen.add(benutzer.getName());
        }
        return namen;
    }
    // public void addMitFahrGast(Benutzer benutzer){
    //     this.mitFahrgaeste.add(benutzer);
    // }
    public boolean removeMitFahrGast(Benutzer benutzer){
        Iterator<Benutzer> it = this.mitFahrgaeste.iterator();
        while(it.hasNext()){
            if(it.next().getId() == benutzer.getId()){
                it.remove();
                this.buchungen -= 1;
                return true;
            }
        }
        return false;
        
        // return this.mitFahrgaeste.removeIf(obj -> obj.getId() == benutzer.getId());
    }
    
    
}
