package com.WebProjekt.MItfahrZentrale.api.ort;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.WebProjekt.MItfahrZentrale.entities.ort.Ort;
import com.WebProjekt.MItfahrZentrale.services.ort.OrtService;

@RestController //Damit Spring Thymeleaf nicht versucht eine View zu rendern, sondern eine JSON zurückgibt
public class OrtAPIController {

    @Autowired private OrtService ortService;

@GetMapping("/api/ort")
public List<OrtDTO> getOrte(){

    List<Ort> alleOrte = ortService.holeAlleOrte();
    List<OrtDTO> ortDTOs = new LinkedList<OrtDTO>();

    for(Ort ort : alleOrte){
        ortDTOs.add(OrtDTO.fromOrt(ort));
    }

    return ortDTOs;
}

@GetMapping("/api/ort/{id}")
public OrtDTO getOrt2(@PathVariable("id") long ortID){
    Ort ort =  ortService.holeOrtMitId(ortID).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ort wurde nicht gefunden"));
    return OrtDTO.fromOrt(ort);
}   
    
}
