package com.WebProjekt.MItfahrZentrale.services.tour;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.WebProjekt.MItfahrZentrale.entities.tour.Tour;
import com.WebProjekt.MItfahrZentrale.entities.tour.TourRepository;

@Service
public class TourServiceImpl implements TourService{

    private TourRepository tourRepository;

    public TourServiceImpl(@Autowired TourRepository tourRepository){
        this.tourRepository = tourRepository;
    }

    @Override
    public List<Tour> holeAlleTouren() {
        return tourRepository.findAll();
    }

    @Override
    public Optional<Tour> holeTourMitId(long id) {
        return tourRepository.findById(id);
    }

    @Override
    public Tour speichereTour(Tour t) {
        Tour tour = tourRepository.save(t);
        return tour;
    }

    @Override
    public void loescheTourMitId(long id) {
        tourRepository.deleteById(id);
    }

    public Tour aktualisiereOrt(Tour t){
        return tourRepository.findById(t.getId()).map(existingTour -> {
            existingTour.setStartOrt(t.getZielOrt());
            existingTour.setStartOrt(t.getStartOrt());
            existingTour.setAnbieter(t.getAnbieter());
            existingTour.setPreis(t.getPreis());
            existingTour.setPlaetze(t.getPlaetze());
            existingTour.setInfo(t.getInfo());
            return tourRepository.save(existingTour); 
        })
        .orElseThrow(() -> new RuntimeException("Tour nicht gefunden"));
    }
    
}
