package com.WebProjekt.MItfahrZentrale.api.ort;

import com.WebProjekt.MItfahrZentrale.entities.ort.Ort;

public record OrtDTO(long id, String name, double geoBreite,  double geoLaenge){

    static OrtDTO fromOrt(Ort o){

        OrtDTO ortDto = new OrtDTO(o.getId(), o.getName(), o.getGeobreite(), o.getGeolaenge());
        return ortDto;
    }    

    static Ort toOrt(OrtDTO dto){
        Ort ort = new Ort();
        ort.setId(dto.id);
        ort.setName(dto.name);
        ort.setGeobreite(dto.geoBreite);
        ort.setGeolaenge(dto.geoLaenge);
        return ort;
    }
}
