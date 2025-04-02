package com.WebProjekt.MItfahrZentrale.services.tour;

import java.time.LocalDateTime;

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
    String info){

    public static TourDTD fromTour(Tour t){
        int buchungen = t.getMitfahrGaeste().size();
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
            t.getInfo());
    }
    
}
