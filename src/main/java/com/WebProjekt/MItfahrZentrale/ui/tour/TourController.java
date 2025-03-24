package com.WebProjekt.MItfahrZentrale.ui.tour;

import java.util.List;
import java.util.Locale;

import javax.management.RuntimeErrorException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.WebProjekt.MItfahrZentrale.entities.ort.Ort;
import com.WebProjekt.MItfahrZentrale.entities.tour.Tour;
import com.WebProjekt.MItfahrZentrale.services.ort.OrtServiceImpl;
import com.WebProjekt.MItfahrZentrale.services.tour.TourServiceImpl;

import jakarta.validation.Valid;


@Controller
@SessionAttributes("tourformular") 
public class TourController {

    Logger logger = LoggerFactory.getLogger(TourController.class);

    @Autowired TourServiceImpl tourService;
    @Autowired OrtServiceImpl ortService;

    @ModelAttribute("tourformular")
    public TourFormular initTourFormular(){
        return new TourFormular();
    }

    @GetMapping("/validiere")
    public String validiereFormular(@Valid @ModelAttribute("tourformular") TourFormular tourFormular,
                                    BindingResult result,
                                    Model model){

        if(result.hasErrors()){
            return "tourbearbeiten";
        }

        Tour tour = new Tour();
        tourFormular.toTour(tour);

        if(tourFormular.getId() == 0){
            try{
                tour = tourService.speichereTour(tour);
            }catch(Exception e){
                logger.error(e.getMessage());
                model.addAttribute("info", e.getMessage());
            }
            
        }else{ 
            try{
                tour = tourService.holeTourMitId(tour.getId()).orElseThrow(() -> new RuntimeException("Tour konnte nicht gefunden werden"));
            }catch(Exception e){
                model.addAttribute("info", e.getMessage());
                logger.error("tour konnte in der Datenbannk nicht gefunden werden");
            }
        }

        tourFormular.fromTour(tour);
        model.addAttribute("tourformular", tourFormular);
        model.addAttribute("tourID", tourFormular.getId());
        List<Ort> ortListe = ortService.holeAlleOrte();
        model.addAttribute("ortListe", ortListe);

        return "tourbearbeiten";

    }


    @PostMapping("/submitTour") //ruft validiere auf, da die Validation nach dem heraussuchen der Ort Objekte stattfinden muss(String != Obj)
    public String submitTour(@ModelAttribute("tourformular") TourFormular tourFormular,
                            Model model){
        
        Ort startOrt = ortService.holeOrtMitId(tourFormular.getStartOrtId()).orElseThrow(() -> new RuntimeException("Ort nicht gefunden"));
        Ort zielOrt = ortService.holeOrtMitId(tourFormular.getZielOrtId()).orElseThrow(() -> new RuntimeException("Ort nicht gefunden"));

        tourFormular.setStartOrt(startOrt);
        tourFormular.setZielOrt(zielOrt);

        return "redirect:/validiere";
    }

    @GetMapping("/tour")
    public String tourListeAusgeben(Model model){

        List<Tour> alleTouren = tourService.holeAlleTouren();
        model.addAttribute("alleTouren", alleTouren);

        return "tourliste";
    }

    @GetMapping("/tour/0")
    public String tourAnlegen(@ModelAttribute("tourformular") TourFormular tourFormular,
                                Model model){

        List<Ort> ortListe = ortService.holeAlleOrte();
        model.addAttribute("tourformular", tourFormular);
        model.addAttribute("tourID", 0);
        model.addAttribute("ortListe", ortListe);

        return "tourbearbeiten";
    }

    @GetMapping("/tour/{n}")
    public String tourAnzeigen(@ModelAttribute("tourformular") TourFormular tourFormular,
                                @PathVariable int n,
                                Model model){

        Tour tour = new Tour();
        try{
            tour = tourService.holeTourMitId(n).orElseThrow(() -> new RuntimeException("Tour konnte nicht gefunden werden."));
        }catch(Exception e){
            logger.error("info", e.getMessage());
            model.addAttribute("info", e.getMessage());
        }

        tourFormular.fromTour(tour);
        model.addAttribute("tourFormular", tourFormular);
        model.addAttribute("tourID", n);
        List<Ort> ortListe = ortService.holeAlleOrte();
        model.addAttribute("ortListe", ortListe);

        return "tourbearbeiten";
    }

    
}
