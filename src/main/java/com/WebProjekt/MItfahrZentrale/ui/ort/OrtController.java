package com.WebProjekt.MItfahrZentrale.ui.ort;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.WebProjekt.MItfahrZentrale.entities.ort.Ort;
import com.WebProjekt.MItfahrZentrale.services.ort.OrtServiceImpl;

import jakarta.validation.Valid;


@Controller
@SessionAttributes("ortFormular")
public class OrtController {

    private static final Logger logger = LoggerFactory.getLogger(OrtController.class);

    @Autowired private OrtServiceImpl ortService;

    @ModelAttribute("ortFormular")
    public OrtFormular initFormular(){
        return new OrtFormular();
    }



    @PostMapping("/submitOrt")
    public String legeOrtAn(@Valid @ModelAttribute("ortFormular") OrtFormular ortFormular,
                            BindingResult result,
                            Model model){

        if(result.hasErrors()){
            return "ortbearbeiten";
        }

        Ort ort = new Ort();
        ortFormular.toOrt(ort);

        if(ortFormular.getId() == 0){
            ort = ortService.speichereOrt(ort);
        }else{
            try{
                ort = ortService.aktualisiereOrt(ort);
            }catch(Exception e){
                logger.error(e.toString());
                model.addAttribute("info", e.toString());
            }
        }
        ortFormular.fromOrt(ort);
        model.addAttribute("ortFormular", ortFormular);
        model.addAttribute("ortID", ort.getId());
        
        return "redirect:/ort/" + ort.getId();
    }

    @GetMapping("/ort/{n}/del")
    public String loescheOrt(@PathVariable int n){

        ortService.loescheOrtMitId(n);

        return "redirect:/ort";
    }


    @GetMapping("/ort")
    public String getOrtListe(@ModelAttribute("ortFormular") OrtFormular ortFormular,
                                Model model){

        List<Ort> alleOrte = ortService.holeAlleOrte();
        model.addAttribute("alleOrte", alleOrte);

        return "ortliste";
    }

    @GetMapping("/ort/0")
    public String neuenOrtAnlegen(Model model){

        model.addAttribute("ortFormular", new OrtFormular());
        model.addAttribute("ortID", 0);
        return "ortbearbeiten";
    }

    @GetMapping("/ort/{n}")
    public String getOrt(@ModelAttribute("ortFormular") OrtFormular ortFormular,
                        @PathVariable int n,
                        Model model){

        try{
            Ort ort = new Ort();
            ort = ortService.holeOrtMitId(n).orElseThrow(() -> new RuntimeException("Ort nicht gefunden"));
            ortFormular.fromOrt(ort);
        }catch(Exception e){
            model.addAttribute("info", e.toString());
            logger.error(e.toString());
        }
        
        model.addAttribute("ortFormular",ortFormular);
        model.addAttribute("ortID", n);

        return "ortbearbeiten";
    }
    
}
