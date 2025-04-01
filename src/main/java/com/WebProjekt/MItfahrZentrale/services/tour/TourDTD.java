package com.WebProjekt.MItfahrZentrale.services.tour;

import java.time.LocalDateTime;

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
    
}
