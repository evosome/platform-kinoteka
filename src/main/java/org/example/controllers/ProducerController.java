package org.example.controllers;

import org.example.modules.Film;
import org.example.modules.Halls;
import org.example.modules.Producer;
import org.example.modules.Session;
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
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "Producers", description = "The Producers API")
@RestController
@RequestMapping("/api1/v1")
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
    @Operation(summary = "Update producer", tags = "producers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated producer",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Producer.class))),
            @ApiResponse(responseCode = "404", description = "Producer not found"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Invalid data"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PutMapping("/producers/{id}")
    public ResponseEntity<Producer> updateProducer(@PathVariable Long id, @RequestBody Producer producerDetails) {
        Producer existingProducer = producerService.getProducerById(id);

        if (existingProducer == null) {
            return ResponseEntity.notFound().build();
        }

        existingProducer.setName(producerDetails.getName());
        existingProducer.setSurname(producerDetails.getSurname());

        if (producerDetails.getDirectorsMovies() != null) {
            existingProducer.getDirectorsMovies().clear();
            existingProducer.getDirectorsMovies().addAll(producerDetails.getDirectorsMovies());
        }

        Producer updatedProducer = producerService.createProducer(existingProducer);

        return ResponseEntity.ok(updatedProducer);
    }
    @DeleteMapping("/producers/{id")
    public void deleteProducer(@PathVariable Long id) {
       Producer producer = producerService.getProducerById(id);
       List<Film> films = producer.getDirectorsMovies();
       producer.removeFilm(films);
       producerService.deleteProducer(id);

    }
}