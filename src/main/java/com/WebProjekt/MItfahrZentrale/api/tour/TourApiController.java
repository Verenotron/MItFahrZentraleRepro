package com.WebProjekt.MItfahrZentrale.api.tour;

import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.WebProjekt.MItfahrZentrale.entities.benutzer.Benutzer;
import com.WebProjekt.MItfahrZentrale.entities.tour.Tour;
import com.WebProjekt.MItfahrZentrale.services.benutzer.BenutzerServiceImpl;
import com.WebProjekt.MItfahrZentrale.services.tour.TourDTD;
import com.WebProjekt.MItfahrZentrale.services.tour.TourServiceImpl;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:8080"}) //Wird benötigt, damit CORS Probleme nicht auftreten
@RestController
public class TourApiController {

    @Autowired TourServiceImpl tourService;
    @Autowired BenutzerServiceImpl benutzerService;

    @GetMapping("/api/tour")
    public List<TourDTD> getTourenListe(){
        List<Tour> alleTouren = tourService.holeAlleTouren();
        List<TourDTD> alleTourenDTD = new LinkedList<TourDTD>();
        for (Tour tour : alleTouren){
            alleTourenDTD.add(TourDTD.fromTour(tour));
        }
        return alleTourenDTD;
    }

    private String validiere(String token){

        SignedJWT signedJWT;
        JWTClaimsSet claims;
        String usermail = null;
        token = token.replace("Bearer ", "");
        try {
            signedJWT = SignedJWT.parse(token);
            claims = signedJWT.getJWTClaimsSet();
            usermail = claims.getSubject();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return usermail;

    }

    private Tour findeInTouren(TourDTD tourDTD){
        List<Tour> alleTouren = tourService.holeAlleTouren();
        for (Tour aktTour : alleTouren){
            if(tourDTD.id() == aktTour.getId()){
                return aktTour;
            }
        }
        return null;
    }
    private Benutzer findeInBenutzer(String email){
        List<Benutzer> alleBenutzer = benutzerService.holeAlleBenutzer();
        for(Benutzer benutzer : alleBenutzer){
            if(benutzer.geteMail().equals(email)){
                return benutzer;
            }
        }
        return null;
    }

    @PostMapping("api/buchungEntfernen")
    public ResponseEntity<String> buchungEntfernen(@RequestHeader("Authorization") String token,
                                @RequestBody TourDTD tourDTD){

        String usermail = validiere(token);
        Tour tour = findeInTouren(tourDTD);
        Benutzer benutzer = findeInBenutzer(usermail);
        if(tour.removeMitFahrGast(benutzer)){
            tourService.aktualisiereTour(tour);
            return ResponseEntity.ok("Benutzer konnte entfernt werden.");
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Benutzer nicht vorhanden.");
        }

        

    }

    @PostMapping("/api/buchen")
    public ResponseEntity<String> getMethodName(@RequestHeader("Authorization") String token,
                                @RequestBody TourDTD tourDTD) {

        String usermail = validiere(token);

        List<Tour> alleTouren = tourService.holeAlleTouren();
        List<Benutzer> alleBenutzer = benutzerService.holeAlleBenutzer();
        Tour gefundeneTour;
        Benutzer gefundenerBenutzer = null;

        for(Benutzer benutzer : alleBenutzer){
            if(benutzer.geteMail().equals(usermail)){
                gefundenerBenutzer = benutzer;
                break;
            }
        }

        for(Tour tour : alleTouren){
            if(tour.getId() == tourDTD.id()){
                gefundeneTour = tour;
                if(gefundeneTour.buche(gefundenerBenutzer)){
                    //gefundeneTour.addMitFahrGast(gefundenerBenutzer);
                    tourService.aktualisiereTour(tour);
                }else{
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tour bereits ausgebucht.");
                }
            }
        }
        return ResponseEntity.ok("Tour erfolgreich gebucht.");
    }
    
}
