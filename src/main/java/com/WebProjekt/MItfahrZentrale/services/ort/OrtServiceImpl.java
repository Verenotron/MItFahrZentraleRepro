package com.WebProjekt.MItfahrZentrale.services.ort;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.WebProjekt.MItfahrZentrale.entities.benutzer.Benutzer;
import com.WebProjekt.MItfahrZentrale.entities.ort.Ort;
import com.WebProjekt.MItfahrZentrale.entities.ort.OrtRepository;

@Service
public class OrtServiceImpl implements OrtService{
    
    private OrtRepository ortRepository;

    public OrtServiceImpl(@Autowired OrtRepository ortRepository){
        this.ortRepository = ortRepository;
    }

    @Override
    public List<Ort> holeAlleOrte() {
        return ortRepository.findAll();
    }

    @Override
    public Optional<Ort> holeOrtMitId(long id) {
        return ortRepository.findById(id);
    }

    @Override
    public Ort speichereOrt(Ort o) {
        Ort ort = ortRepository.save(o);
        return ort;
    }

    @Override
    public void loescheOrtMitId(long id) {
        ortRepository.deleteById(id);
    }

    public Ort aktualisiereOrt(Ort o){
        return ortRepository.findById(o.getId()).map(existingOrt -> {
            existingOrt.setName(o.getName());
            existingOrt.setGeobreite(o.getGeobreite());
            existingOrt.setGeolaenge(o.getGeolaenge());
            return ortRepository.save(existingOrt); 
        })
        .orElseThrow(() -> new RuntimeException("Benutzer nicht gefunden"));
    }
    
}
