package org.example.controllers;

import org.example.modules.Producer;
import org.example.services.ProducerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "Producers", description = "The Producers API")
@RestController
@RequestMapping("/api1/v1")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProducerController {
    public static ProducerService producerService;
    @Autowired
    public ProducerController(ProducerService producerService) {
        ProducerController.producerService = producerService;
    }
    @Operation(summary = "Gets all producers", tags = "producers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the producers",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Producer.class))))
    })
    @GetMapping("/producer")
    public Page<Producer> getProducerSession(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return producerService.getAllProducer(page, size);
    }
    @Operation(summary = "Create new producer", tags = "producers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created producer",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Producer.class)))
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/producer")
    public Producer createProducer(@RequestBody Producer producer){
        producerService.createProducer(producer);
        return producer;
    }
    @Operation(summary = "Get producer by id", tags = "producers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found producer with id",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Producer.class))),
            @ApiResponse(responseCode = "404", description = "Producer not found")
    })
    @GetMapping("/producer/{id}")
    public Producer getProducerById(@PathVariable Long id){
        return producerService.getProducerById(id);
    }
}