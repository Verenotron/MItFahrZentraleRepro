package com.WebProjekt.MItfahrZentrale.services.benutzer;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.WebProjekt.MItfahrZentrale.entities.benutzer.Benutzer;
import com.WebProjekt.MItfahrZentrale.entities.benutzer.BenutzerRepository;

@Service
public class BenutzerServiceImpl implements BenutzerService{

    private BenutzerRepository benutzerRepository;

    public BenutzerServiceImpl(@Autowired BenutzerRepository benutzerRepository){//Dependency Injection
        this.benutzerRepository = benutzerRepository;
    }

    @Override
    public List<Benutzer> holeAlleBenutzer() {
        return benutzerRepository.findAll();
    }

    @Override
    public Optional<Benutzer> holeBenutzerMitId(long id) { //Optional ist ein Container-Objekt, dass ebntweder ein Objekt vom Typ T enthält, oder leer ist.
        return benutzerRepository.findById(id); //falls Benutzer mit ID nicht vohanden, wird leeres Optional zurückgegeben.
    }

    @Override
    public Benutzer speichereBenutzer(Benutzer b) {
        //Wenn es den Benutzer noch nicht gibt, generiert JPA automatisch eine neue ID, versionsfeld wird auch automatisch gesetzt
        Benutzer benutzer = benutzerRepository.save(b);
        return benutzer;
    }

    @Override
    public void loescheBenutzerMitId(long id) {
        benutzerRepository.deleteById(id);
    }

    // public Optional<Benutzer> findeBenutzer(String email){
    //     List<Benutzer> alleBenutzer = this.holeAlleBenutzer();
    //     for(Benutzer benutzer : alleBenutzer){
    //         if(benutzer.geteMail().equals(email)){
    //             return Optional.of(benutzer); //Damit ich einen Optional zurückgebe
    //         }
    //     }
    //     return Optional.empty();
    // }

    public Benutzer aktualisiereBenutzer(Benutzer b){ // Aktualisiert vorhandenen Benutzer
        return benutzerRepository.findById(b.getId()).map(existingUser -> {
            existingUser.setName(b.getName());
            existingUser.seteMail(b.geteMail());
            existingUser.setGeburtstag(b.getGeburtstag());
            existingUser.setMag(b.getMag());
            existingUser.setMagNicht(b.getMagNicht());
            if(b.getPasswort() != null && !b.getPasswort().isEmpty()){  //Falls PasswortString leer ist, wird altes Passwort übernommen. Kann man auch anders machen siehe Blatt4 S.4+5
                existingUser.setPasswort(b.getPasswort());
            }
            return benutzerRepository.save(existingUser); 
        })
        .orElseThrow(() -> new RuntimeException("Benutzer nicht gefunden"));
    }
    
}
