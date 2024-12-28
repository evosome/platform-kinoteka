package org.example.controllers;

import org.example.modules.Cinemas;
import org.example.services.CinemasService;
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
@Tag(name = "Cinemas", description = "The Cinemas API")
@RestController
@RequestMapping("/api1/v1")
public class CinemasController {
    public static CinemasService cinemasServices;
    @Autowired
    public CinemasController(CinemasService cinemasServices){
        CinemasController.cinemasServices = cinemasServices;
    }
    @Operation(summary = "Gets all cinemases", tags = "cinemas")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found the cinemases",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Cinemas.class)))
                    })
    })
    @GetMapping("/cinemas")
    public Page<Cinemas> getCinemaSession(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return cinemasServices.getAllCinemas(page, size);
    }
    @Operation(summary = "Create new cinemas", tags = "cinemas")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Add new cinemas",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Cinemas.class)))
                    })
    })
    @PostMapping("/cinemas")
    public Cinemas createCinemas(@RequestBody Cinemas cinemas){
        cinemasServices.createCinemas(cinemas);
        return cinemas;
    }
    @Operation(summary = "Get cinemas by id", tags = "cinemas")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found cinemas with id",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Cinemas.class)))
                    })
    })
    @GetMapping("/cinemas/{id}")
    public Cinemas getFeedbackById(@PathVariable Long id){
        return cinemasServices.getCinemasById(id);
    }
}