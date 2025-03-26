package com.WebProjekt.MItfahrZentrale.services.ort;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.WebProjekt.MItfahrZentrale.entities.ort.Ort;
import com.WebProjekt.MItfahrZentrale.entities.ort.OrtRepository;
import com.WebProjekt.MItfahrZentrale.services.geo.GeoAdresse;
import com.WebProjekt.MItfahrZentrale.services.geo.GeoServiceImpl;

@Service
public class OrtServiceImpl implements OrtService{
    
    private OrtRepository ortRepository;
    private GeoServiceImpl geoService;

    public OrtServiceImpl(@Autowired OrtRepository ortRepository, @Autowired GeoServiceImpl geoService){
        this.ortRepository = ortRepository;
        this.geoService = geoService;
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

    @Override
    public List<Ort> findeOrtsvorschlaegeFuerAdresse(String ort) {

        List<GeoAdresse> geoAdressen = geoService.findeAdressen(ort);
        List<Ort> orte = new LinkedList<Ort>();

        Ort nextOrt = new Ort();

        if(geoAdressen.size() == 0){
            return orte;
        }

        for(GeoAdresse currAdr : geoAdressen){
            nextOrt.setName(currAdr.name());
            nextOrt.setGeolaenge(currAdr.lon());
            nextOrt.setGeobreite(currAdr.lat());
            orte.add(nextOrt);
        }

        return orte;
    }
    
}
