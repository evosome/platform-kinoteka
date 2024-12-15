package org.example.controllers;

import org.example.modules.*;
import org.example.services.HallsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "Halls", description = "The Halls API")
@RestController
@RequestMapping("/api1/v1")
@CrossOrigin
public class HallsController {
    public static HallsService hallsService;
    @Autowired
    public HallsController(HallsService hallsService) {
        HallsController.hallsService = hallsService;
    }
    @Operation(summary = "Gets all halls", tags = "halls")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the halls",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Halls.class))))
    })
    @GetMapping("/hall")
    public Page<Halls> getHalls(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        return hallsService.getAllHalls(page, size);
    }
    @Operation(summary = "Create new hall", tags = "halls")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Add new hall",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Halls.class)))
    })
    @PostMapping("/hall/{cinemasId},{hallId}")
    public Halls createHalls(@PathVariable int cinemasId, @PathVariable int hallId, @RequestBody Halls hall){
        Cinemas cinemas = CinemasController.cinemasServices.getCinemasById(cinemasId);
        cinemas.addHalls(hall);
        HallLayout hallLayout = HallLayoutController.hallLayoutService.getHallLayoutById((long) hallId);
        hallLayout.addLay(hall);
        hallsService.createHalls(hall);
        return hall;
    }
    @Operation(summary = "Get hall by id", tags = "halls")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found hall with id",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Halls.class)))
    })
    @GetMapping("/hall/{id}")
    public Halls getHallsById(@PathVariable Long id){
        return hallsService.getHallsById(id);
    }
}