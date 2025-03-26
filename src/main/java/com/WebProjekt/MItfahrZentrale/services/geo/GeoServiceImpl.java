package com.WebProjekt.MItfahrZentrale.services.geo;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class GeoServiceImpl implements GeoService{

    private static Logger logger = LoggerFactory.getLogger(GeoServiceImpl.class);

    @Override
    public List<GeoAdresse> findeAdressen(String ort) {

        // Beispiel URL https://nominatim.openstreetmap.org/search?q=karlsruhe&format=json&countrycodes=de
    
        // if(ort == null){
        //     logger.error("Ort ist null");
        //     return null;
        // }

        // Wunsch-Ergebnisrepräsentation ist JSON
        WebClient client = WebClient.create("https://nominatim.openstreetmap.org");
        List<GeoAdresse> antworten = client.get() //startet einen HTTP-GET Request mit dem WebClient
        .uri("/search?q={n}&format=json&countrycodes=de", ort) 
        // /search ist der Endpunkt der API,, q = Query, ?q={n} -> n wird durch ort ersetzt, countrycodes=de -> nur in Deutschland
        .accept(MediaType.APPLICATION_JSON) //Antwort soll in JSON geliefert werden.
        .retrieve() //Führt die Anfrage aus und wartet auf Antwort
        .bodyToFlux(GeoAdresse.class) // Wandelt Antwort in GeoAdresse-Objekt um. Flux statt Moni, da mehrere Adressen zurückgegeben werden können.
        .collectList() // wandelt den Flux in eine List<GeoAdresse> um
        .block(); // blockiert den aktuellen Thread, bis die Antwort vollständig verarbeitet wruden(nötig, da WebClient standardmäßig asynchron arbeitet).
    
        List<GeoAdresse> geoOrte = antworten;

        if (geoOrte.size() == 0) {
            logger.warn("No address found");
        }
        
        return geoOrte;
    
    }    
        
}
