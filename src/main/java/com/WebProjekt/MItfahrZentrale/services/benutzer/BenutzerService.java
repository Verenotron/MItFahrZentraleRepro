package com.WebProjekt.MItfahrZentrale.services.benutzer;

import java.util.List;
import java.util.Optional;

import com.WebProjekt.MItfahrZentrale.entities.benutzer.Benutzer;

public interface BenutzerService {
    
    List<Benutzer> holeAlleBenutzer();
    Optional<Benutzer> holeBenutzerMitId(long id);
    Benutzer speichereBenutzer(Benutzer b);
    void loescheBenutzerMitId(long id);

}
