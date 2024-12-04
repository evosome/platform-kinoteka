package com.evotek.aboutfilms.controllers;

import com.evotek.aboutfilms.modules.HallLayout;
import com.evotek.aboutfilms.services.HallLayoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api1/v1")
public class HallLayoutController {
    public static HallLayoutService hallLayoutService;
    @Autowired
    public HallLayoutController(HallLayoutService hallLayoutService) {
        HallLayoutController.hallLayoutService = hallLayoutService;
    }
    @GetMapping("/lay")
    public List<HallLayout> getHalls(){
        return hallLayoutService.getAllHallLayout();
    }
    @PostMapping("/lay")
    public HallLayout createHalls(@RequestBody HallLayout lay){
        hallLayoutService.createHallLayout(lay);
        return lay;
    }
    @GetMapping("/lay/{id}")
    public HallLayout getHallsById(@PathVariable Long id){
        return hallLayoutService.getHallLayoutById(id);
    }
}
