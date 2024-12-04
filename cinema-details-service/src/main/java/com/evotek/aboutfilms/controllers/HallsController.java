package com.evotek.aboutfilms.controllers;


import com.evotek.aboutfilms.modules.Cinemas;
import com.evotek.aboutfilms.modules.HallLayout;
import com.evotek.aboutfilms.modules.Halls;
import com.evotek.aboutfilms.services.HallsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api1/v1")
public class HallsController {
    public static HallsService hallsService;
    @Autowired
    public HallsController(HallsService hallsService) {
        HallsController.hallsService = hallsService;
    }
    @GetMapping("/hall")
    public List<Halls> getHalls(){
        return hallsService.getAllHalls();
    }
    @PostMapping("/hall/{cinemasId},{hallId}")
    public Halls createHalls(@PathVariable int cinemasId, @PathVariable int hallId, @RequestBody Halls hall){
        Cinemas cinemas = CinemasController.cinemasServices.getCinemasById(cinemasId);
        cinemas.addHalls(hall);
        HallLayout hallLayout = HallLayoutController.hallLayoutService.getHallLayoutById((long) hallId);
        hallLayout.addLay(hall);
        hallsService.createHalls(hall);
        return hall;
    }
    @GetMapping("/hall/{id}")
    public Halls getHallsById(@PathVariable Long id){
        return hallsService.getHallsById(id);
    }
}
