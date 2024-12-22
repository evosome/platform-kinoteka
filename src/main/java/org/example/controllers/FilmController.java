package org.example.controllers;

import lombok.AllArgsConstructor;
import org.example.modules.*;
import org.example.services.FeedbackService;
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
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "Films", description = "The Films API")
@RestController
@RequestMapping("/api1/v1")
@CrossOrigin(origins = "*", maxAge = 3600)
@AllArgsConstructor
public class FilmController {

    private final FilmServices filmServices;

    private final FeedbackService feedbackService;

    @Operation(summary = "Gets all films", tags = "films")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the films",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Film.class))))
    })
    @GetMapping("/film")
    public Page<Film> getFilm(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) List<String> genreNames,
            @RequestParam(required = false) List<String> countryNames,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Integer ageRestriction,
            @RequestParam(required = false) Long hallId) {
        return filmServices.getAllFilm(page, size, genreNames, countryNames, title, ageRestriction, hallId);
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
    @Operation(summary = "Delete feedback by id", tags = "feedback")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete feedback with id",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Feedback.class)))
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/film/{filmId}")
    public void deleteFilm(@PathVariable long filmId) {
        Film film = filmServices.getFilmById(filmId);
        List<Genre> genres = film.getGenres();
        film.removeGenre(genres);
        List<Producer> producers = film.getProducers();
        film.removeProducer(producers);
        List<Country> countries = film.getCountries();
        film.removeCountry(countries);
        filmServices.deleteFilmById(filmId);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/film/{filmId}")
    public ResponseEntity<Film> updateFilm(@PathVariable long filmId, @RequestBody Film updatedFilm) {
        Film existingFilm = filmServices.getFilmById(filmId);
        if (existingFilm == null) {
            return ResponseEntity.notFound().build();
        }
        existingFilm.setTitle(updatedFilm.getTitle());
        existingFilm.setYear(updatedFilm.getYear());
        existingFilm.setDuration(updatedFilm.getDuration());
        existingFilm.setAgeRestriction(updatedFilm.getAgeRestriction());
        existingFilm.setReleased(updatedFilm.isReleased());
        existingFilm.setBoxOffice(updatedFilm.isBoxOffice());
        existingFilm.setDescription(updatedFilm.getDescription());
        existingFilm.setMark(updatedFilm.getMark());
        existingFilm.setCover(updatedFilm.getCover());
        existingFilm.setProducers(updatedFilm.getProducers());
        existingFilm.setGenres(updatedFilm.getGenres());
        existingFilm.setCountries(updatedFilm.getCountries());
        filmServices.createFilm(existingFilm);

        return ResponseEntity.ok(existingFilm);
    }
    @GetMapping("/film/{id}/feedbacks")
    public Page<Feedback> getAssociatedFeedbacks(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return feedbackService.getFeedbacksByMovieId(id, page, size);
    }
}