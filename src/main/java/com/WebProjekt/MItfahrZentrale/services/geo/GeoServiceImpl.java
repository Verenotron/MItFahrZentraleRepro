package com.WebProjekt.MItfahrZentrale.services.geo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
public class GeoServiceImpl implements GeoService{

    private static Logger logger = LoggerFactory.getLogger(GeoServiceImpl.class);

    private final WebClient webClient;

    public GeoServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://nominatim.openstreetmap.org").build();
    }

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
        .filter(adresse -> adresse.addresstype().equals("city")
                            || adresse.addresstype().equals("town")
                            || adresse.addresstype().equals("village"))
        .collectList() // wandelt den Flux in eine List<GeoAdresse> um
        .block(); // blockiert den aktuellen Thread, bis die Antwort vollständig verarbeitet wruden(nötig, da WebClient standardmäßig asynchron arbeitet).
    
        List<GeoAdresse> geoOrte = antworten;

        if (geoOrte.size() == 0) {
            logger.warn("No address found");
        }
        return geoOrte;
    }    

    // @Override
    // public List<GeoAdresse> findeAdressen(String ort){

    //     if (ort == null) {
    //         logger.error("Der übergebene Ort ist null.");
    //         return new ArrayList<>();
    //     }

    //     try {
    //         return webClient.get()
    //                 .uri(uriBuilder -> uriBuilder
    //                         .path("/search")
    //                         .queryParam("q", ort)
    //                         .queryParam("format", "json")
    //                         .queryParam("countrycodes", "de")
    //                         .build())
    //                 .retrieve()
    //                 .bodyToMono(GeoAdresse[].class)
    //                 .blockOptional()
    //                 .map(List::of)
    //                 .orElse(new ArrayList<>())
    //                 .stream()
    //                 .filter(adresse -> adresse.addresstype().equals("city")
    //                         || adresse.addresstype().equals("town")
    //                         || adresse.addresstype().equals("village"))
    //                 .collect(Collectors.toList());
    //     } catch (WebClientResponseException e) {
    //         logger.error("Fehler bei der API-Abfrage: {}", e.getMessage());
    //         return new ArrayList<>();
    //     } catch (Exception e) {
    //         logger.error("Unerwarteter Fehler: {}", e.getMessage());
    //         return new ArrayList<>();
    //     }
    // }
    }
     
