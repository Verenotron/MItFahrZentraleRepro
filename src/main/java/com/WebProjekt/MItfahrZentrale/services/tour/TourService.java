package com.WebProjekt.MItfahrZentrale.services.tour;

import java.util.List;
import java.util.Optional;

import com.WebProjekt.MItfahrZentrale.entities.tour.Tour;

public interface TourService{

    List<Tour> holeAlleTouren();
    Optional<Tour> holeTourMitId(long id);
    Tour speichereTour(Tour t);
    void loescheTourMitId(long id);
    
}
