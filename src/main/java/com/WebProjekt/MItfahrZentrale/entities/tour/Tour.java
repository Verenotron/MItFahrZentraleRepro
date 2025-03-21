package com.WebProjekt.MItfahrZentrale.entities.tour;

import java.time.LocalDateTime;

import com.WebProjekt.MItfahrZentrale.entities.benutzer.Benutzer;
import com.WebProjekt.MItfahrZentrale.entities.ort.Ort;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Version;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
public class Tour {

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
    
    @Max(400)
    private String info;

    @ManyToOne
    private Benutzer anbieter;

    @NotNull
    @ManyToOne
    private Ort startOrt;

    @NotNull
    @ManyToOne
    private Ort zielOrt;

    public Ort getZielOrt() {
        return zielOrt;
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

    
    
}
