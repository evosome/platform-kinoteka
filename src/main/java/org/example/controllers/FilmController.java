package org.example.controllers;

import org.example.modules.Film;
import org.example.services.FilmServices;
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
@Tag(name = "Films", description = "The Films API")
@RestController
@RequestMapping("/api1/v1")
@CrossOrigin
public class FilmController {
    public static FilmServices filmServices;
    @Autowired
    public FilmController(FilmServices filmServices){
        FilmController.filmServices = filmServices;
    }
    @Operation(summary = "Gets all films", tags = "films")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the films",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Film.class))))
    })
    @GetMapping("/film")
    public Page<Film> getFilm(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {
            return filmServices.getAllFilm(page, size);
    }
    @Operation(summary = "Create new film", tags = "films")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created film",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Film.class)))
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/film")
    public Film createFilm(@RequestBody Film film){
        film.addProducer(film);
        film.addCountry(film);
        film.addGenre(film);
        filmServices.createFilm(film);
        return film;
    }
    @Operation(summary = "Get film by id", tags = "films")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found film with id",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Film.class))),
            @ApiResponse(responseCode = "404", description = "Film not found") // обработка ошибки
    })
    @GetMapping("/film/{id}")
    public Film getFilmById(@PathVariable Long id){
//        Film film = filmServices.getFilmById(id);
//        return FilmToFilmDto(film);
        return filmServices.getFilmById(id);
    }
}