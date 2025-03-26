package com.WebProjekt.MItfahrZentrale.services.geo;

import java.util.List;

public interface GeoService {

    List<GeoAdresse> findeAdressen(String ort);
    
}
