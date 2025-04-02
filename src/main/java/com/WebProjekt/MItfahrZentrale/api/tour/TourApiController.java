package com.WebProjekt.MItfahrZentrale.api.tour;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.WebProjekt.MItfahrZentrale.entities.tour.Tour;
import com.WebProjekt.MItfahrZentrale.services.tour.TourDTD;
import com.WebProjekt.MItfahrZentrale.services.tour.TourServiceImpl;

@RestController
public class TourApiController {

    @Autowired TourServiceImpl tourService;

    @GetMapping("/api/tour")
    public List<TourDTD> getTourenListe(){
        List<Tour> alleTouren = tourService.holeAlleTouren();
        List<TourDTD> alleTourenDTD = new LinkedList();
        for (Tour tour : alleTouren){
            alleTourenDTD.add(TourDTD.fromTour(tour));
        }
        return alleTourenDTD;
    }
    
}
