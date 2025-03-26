package com.WebProjekt.MItfahrZentrale.services.geo;

public record GeoAdresse (
    String name,
    String addresstype,
    String display_name,
    double lat,
    double lon){
    
}
