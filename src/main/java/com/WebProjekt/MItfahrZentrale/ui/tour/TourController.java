package com.WebProjekt.MItfahrZentrale.ui.tour;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.WebProjekt.MItfahrZentrale.entities.benutzer.Benutzer;
import com.WebProjekt.MItfahrZentrale.entities.ort.Ort;
import com.WebProjekt.MItfahrZentrale.entities.tour.Tour;
import com.WebProjekt.MItfahrZentrale.messaging.FrontendNachrichtEvent;
import com.WebProjekt.MItfahrZentrale.messaging.FrontendNachrichtEvent.Action;
import com.WebProjekt.MItfahrZentrale.messaging.FrontendNachrichtEvent.Typ;
import com.WebProjekt.MItfahrZentrale.messaging.FrontendNachrichtServiceImpl;
import com.WebProjekt.MItfahrZentrale.services.benutzer.BenutzerServiceImpl;
import com.WebProjekt.MItfahrZentrale.services.ort.OrtServiceImpl;
import com.WebProjekt.MItfahrZentrale.services.tour.TourServiceImpl;

import jakarta.validation.Valid;


@Controller
@SessionAttributes("tourformular")
@RequestMapping("/admin")
public class TourController {

    Logger logger = LoggerFactory.getLogger(TourController.class);

    @Autowired TourServiceImpl tourService;
    @Autowired OrtServiceImpl ortService;
    @Autowired BenutzerServiceImpl benutzerService;

    @Autowired FrontendNachrichtServiceImpl nachrichtenService;

    @ModelAttribute("tourformular")
    public TourFormular initTourFormular(){
        return new TourFormular();
    }

    @GetMapping("")
    public String rufeTourenListeauf(){
        return "redirect:/admin/tour";
    }

    @GetMapping("/tour/{n}/del")
    public String loescheTour(@PathVariable int n, RedirectAttributes redirectAttributes, Model model){

        var auth = SecurityContextHolder.getContext().getAuthentication();
        Tour tour = tourService.holeTourMitId(n).orElseThrow(() -> new RuntimeException("Tour konnte nicht geladen werden."));
        if(!auth.getName().equals(tour.getAnbieter().geteMail())){
            redirectAttributes.addFlashAttribute("info", "Tour darf nur vom Anbieter gelöscht werden.");
            return "redirect:/admin/tour";
        }

        try{
            tourService.loescheTourMitId(n);
        }catch(Exception e){
            model.addAttribute("info", e.getMessage());
            logger.error(e.getMessage());
        }

        FrontendNachrichtEvent event = new FrontendNachrichtEvent(Typ.TOUR, n, Action.DELETE);
        nachrichtenService.sendEvent(event);

        return "redirect:/admin/tour";
    }

    @GetMapping("/validiere")
    public String validiereFormular(@Valid @ModelAttribute("tourformular") TourFormular tourFormular,
                                    BindingResult result,
                                    Model model,
                                    SessionStatus sessionStatus){

        if(result.hasErrors()){
            model.addAttribute("tourID", 0); //Damit "neue Tour" als Überschrift gewählt wird.
            return "tourbearbeiten";
        }

        Tour tour = new Tour();
        tourFormular.toTour(tour);

        if(tourFormular.getId() == 0){
            try{
                tour = tourService.speichereTour(tour);
                FrontendNachrichtEvent event = new FrontendNachrichtEvent(Typ.TOUR, tour.getId(), Action.CREATE);
                nachrichtenService.sendEvent(event);
            }catch(Exception e){
                logger.error(e.getMessage());
                model.addAttribute("info", e.getMessage());
            }
            
         }//else{ 
        //     try{
        //         tour = tourService.holeTourMitId(tour.getId()).orElseThrow(() -> new RuntimeException("Tour konnte nicht gefunden werden"));
        //     }catch(Exception e){
        //         model.addAttribute("info", e.getMessage());
        //         logger.error("Tour konnte in der Datenbannk nicht gefunden werden");
        //     }
        // }

        tourFormular.fromTour(tour);
        model.addAttribute("tourformular", tourFormular);
        model.addAttribute("tourID", tourFormular.getId());
        List<Ort> ortListe = ortService.holeAlleOrte();
        List<Benutzer> anbieterListe = benutzerService.holeAlleBenutzer();
        model.addAttribute("ortListe", ortListe);
        model.addAttribute("anbieterListe", anbieterListe);

        sessionStatus.setComplete(); //session aktiv beenden

        return "tourbearbeiten";

    }


    @PostMapping("/submitTour") //ruft validiere auf, da die Validation nach dem heraussuchen der Ort Objekte stattfinden muss(String != Obj)
    public String submitTour(@ModelAttribute("tourformular") TourFormular tourFormular,
                            BindingResult result,
                            Model model){

        try{
            Ort startOrt = ortService.holeOrtMitId(tourFormular.getStartOrtId()).orElseThrow(() -> new RuntimeException("Ort nicht gefunden"));
            Ort zielOrt = ortService.holeOrtMitId(tourFormular.getZielOrtId()).orElseThrow(() -> new RuntimeException("Ort nicht gefunden"));
            Benutzer benutzer = benutzerService.holeBenutzerMitId(tourFormular.getAnbieterId()).orElseThrow(() -> new RuntimeException("Benutzer (Anbieter) nicht gefunden"));
            tourFormular.setStartOrt(startOrt);
            tourFormular.setZielOrt(zielOrt);
            tourFormular.setAnbieter(benutzer);
            tourFormular.setBuchungen(0);
        }catch(Exception e){
            model.addAttribute("info", e.getMessage());
            logger.error(e.getMessage());
        }

        return "redirect:/admin/validiere";
    }

    @GetMapping("/tour")
    public String tourListeAusgeben(SessionStatus sessionStatus,
                                    Model model){

        List<Tour> alleTouren = tourService.holeAlleTouren();
        model.addAttribute("alleTouren", alleTouren);

        sessionStatus.setComplete();

        return "tourliste";
    }

    @GetMapping("/tour/0")
    public String tourAnlegen(@ModelAttribute("tourformular") TourFormular tourFormular,
                                Model model){
        
        List<Ort> ortListe = ortService.holeAlleOrte();
        List<Benutzer> anbieterListe = benutzerService.holeAlleBenutzer();
        model.addAttribute("tourformular", tourFormular);
        model.addAttribute("tourID", 0);
        model.addAttribute("ortListe", ortListe);
        model.addAttribute("anbieterListe", anbieterListe);

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
        List<Benutzer> anbieterListe = benutzerService.holeAlleBenutzer();
        model.addAttribute("ortListe", ortListe);
        model.addAttribute("anbieterListe", anbieterListe);

        return "tourbearbeiten";
    }

    
}
