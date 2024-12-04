package com.evotek.film.controllers;

import com.evotek.film.modules.Producer;
import com.evotek.film.services.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api1/v1")
public class ProducerController {
    public static ProducerService producerService;
    @Autowired
    public ProducerController(ProducerService producerService) {
        ProducerController.producerService = producerService;
    }
    @GetMapping("/producer")
    public List<Producer> getProducerSession(){
        return producerService.getAllProducer();
    }
    @PostMapping("/producer")
    public Producer createProducer(@RequestBody Producer producer){
        producerService.createProducer(producer);
        return producer;
    }
    @GetMapping("/producer/{id}")
    public Producer getProducerById(@PathVariable Long id){
        return producerService.getProducerById(id);
    }
}
