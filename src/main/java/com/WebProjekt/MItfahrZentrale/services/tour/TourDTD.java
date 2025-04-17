package com.WebProjekt.MItfahrZentrale.services.tour;

import java.time.LocalDateTime;
import java.util.List;

import com.WebProjekt.MItfahrZentrale.entities.benutzer.Benutzer;
import com.WebProjekt.MItfahrZentrale.entities.tour.Tour;

public record TourDTD (
    long id,
    LocalDateTime abfahrDateTime,
    int preis,
    int plaetze,
    int buchungen,
    String startOrtName,
    long startOrtId,
    String zielOrtName,
    long zielOrtId,
    String anbieterName,
    long anbieterId,
    double distanz,
    String info,
    List<String> mitFahrGaesteNamen){

    public static TourDTD fromTour(Tour t){
        int buchungen = t.getMitFahrgaeste().size();
        return new TourDTD(
            t.getId(),
            t.getAbfahrDateTime(),
            t.getPreis(),
            t.getPlaetze(),
            buchungen,
            t.getStartOrtName(),
            t.getStartOrtId(),
            t.getZielOrtName(),
            t.getZielOrtId(),
            t.getAnbieterName(),
            t.getAnbieterId(),
            t.getDistanz(),
            t.getInfo(),
            t.getMitFahrGastNamen());
    }
    
}
