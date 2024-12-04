package com.evotek.aboutfilms.controllers;

import com.evotek.aboutfilms.modules.Cinemas;
import com.evotek.aboutfilms.services.CinemasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api1/v1")
public class CinemasController {
    public static CinemasService cinemasServices;
    @Autowired
    public CinemasController(CinemasService cinemasServices){
        CinemasController.cinemasServices = cinemasServices;
    }
    @GetMapping("/cinemas")
    public List<Cinemas> getCinemaSession(){
        return cinemasServices.getAllCinemas();
    }
    @PostMapping("/cinemas")
    public Cinemas createCinemas(@RequestBody Cinemas cinemas){
        cinemasServices.createCinemas(cinemas);
        return cinemas;
    }
    @GetMapping("/cinemas/{id}")
    public Cinemas getFeedbackById(@PathVariable Long id){
        return cinemasServices.getCinemasById(id);
    }
}