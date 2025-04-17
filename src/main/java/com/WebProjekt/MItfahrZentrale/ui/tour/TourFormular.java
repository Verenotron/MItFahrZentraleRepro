package com.WebProjekt.MItfahrZentrale.ui.tour;

import java.time.LocalDateTime;
import java.util.List;

import com.WebProjekt.MItfahrZentrale.entities.benutzer.Benutzer;
import com.WebProjekt.MItfahrZentrale.entities.ort.Ort;
import com.WebProjekt.MItfahrZentrale.entities.tour.Tour;

import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public class TourFormular {

    private long id;
    private long version;

    @NotNull
    private LocalDateTime abfahrDateTime;

    @PositiveOrZero
    private int preis;

    @Min(1)
    private int plaetze;

    private int buchungen;
    
    @Size(max = 400)
    private String info;

    @ManyToOne
    @NotNull
    private Benutzer anbieter;

    @ManyToOne
    @NotNull
    private Ort startOrt;

    @ManyToOne
    @NotNull
    private Ort zielOrt;

    private List<Benutzer> mitFahrGaeste;

    private long startOrtId;
    private long zielOrtId; //Später wieder rausnehmen, falls es eine bessere Lösung gibt? 
    private long anbieterId;

    public void toTour(Tour tour){
        tour.setId(this.id);
        tour.setAbfahrDateTime(this.abfahrDateTime);
        tour.setPreis(this.preis);
        tour.setBuchungen(this.buchungen);
        tour.setPlaetze(this.plaetze);
        tour.setInfo(this.info);
        tour.setStartOrt(this.startOrt);
        tour.setZielOrt(this.zielOrt);
        tour.setAnbieter(this.anbieter);
    }

    public void fromTour(Tour tour){
        this.id = tour.getId();
        this.abfahrDateTime = tour.getAbfahrDateTime();
        this.preis = tour.getPreis();
        this.buchungen = tour.getBuchungen();
        this.plaetze = tour.getPlaetze();
        this.info = tour.getInfo();
        this.startOrt = tour.getStartOrt();
        this.zielOrt = tour.getZielOrt();
        this.anbieter = tour.getAnbieter();
    }

    

    public long getStartOrtId() {
        return startOrtId;
    }

    public void setStartOrtId(long startOrtId) {
        this.startOrtId = startOrtId;
    }

    public long getZielOrtId() {
        return zielOrtId;
    }

    public void setZielOrtId(long zielOrtId) {
        this.zielOrtId = zielOrtId;
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

    public Benutzer getAnbieter() {
        return anbieter;
    }

    public void setAnbieter(Benutzer anbieter) {
        this.anbieter = anbieter;
    }

    public Ort getStartOrt() {
        return startOrt;
    }

    public void setStartOrt(Ort startOrt) {
        this.startOrt = startOrt;
    }

    public Ort getZielOrt() {
        return zielOrt;
    }

    public void setZielOrt(Ort zielOrt) {
        this.zielOrt = zielOrt;
    }

    public long getAnbieterId() {
        return anbieterId;
    }

    public void setAnbieterId(long anbieterId) {
        this.anbieterId = anbieterId;
    }

    public int getBuchungen() {
        return buchungen;
    }

    public void setBuchungen(int buchungen) {
        this.buchungen = buchungen;
    }

    public List<Benutzer> getMitFahrGaeste() {
        return mitFahrGaeste;
    }

    public void setMitFahrGaeste(List<Benutzer> mitFahrGaeste) {
        this.mitFahrGaeste = mitFahrGaeste;
    }

    public void addGast(Benutzer gast){
        this.mitFahrGaeste.add(gast);
    }
    
}
