package com.WebProjekt.MItfahrZentrale.services.ort;

import java.util.List;
import java.util.Optional;

import com.WebProjekt.MItfahrZentrale.entities.ort.Ort;

public interface OrtService {
    
    List<Ort> holeAlleOrte();
    Optional<Ort> holeOrtMitId(long id);
    Ort speichereOrt(Ort o);
    void loescheOrtMitId(long id);

    List<Ort> findeOrtsvorschlaegeFuerAdresse(String ort);
}
