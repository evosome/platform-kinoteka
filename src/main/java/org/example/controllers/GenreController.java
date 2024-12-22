package org.example.controllers;

import org.example.modules.Genre;
import org.example.services.GenreService;
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
@Tag(name = "Genres", description = "The Genres API")
@RestController
@RequestMapping("/api1/v1")
public class GenreController {
    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }
    @Operation(summary = "Gets all genres", tags = "genres")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the genres",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Genre.class))))
    })
    @GetMapping("/genres")
    public Page<Genre> getAllGenres(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return genreService.getAllGenres(page, size);
    }
    @Operation(summary = "Create new genre", tags = "genres")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created genre",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Genre.class)))
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/genres")
    public Genre createGenre(@RequestBody Genre genre) {
        return genreService.createGenre(genre);
    }
    @Operation(summary = "Get genre by id", tags = "genres")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found genre with id",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Genre.class))),
            @ApiResponse(responseCode = "404", description = "Genre not found")
    })
    @GetMapping("/genres/{id}")
    public Genre getGenreById(@PathVariable Long id) {
        return genreService.getGenreById(id);
    }
    @Operation(summary = "Update genre", tags = "genres")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated genre",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Genre.class))),
            @ApiResponse(responseCode = "404", description = "Genre not found"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Invalid data"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/genres/{id}")
    public ResponseEntity<Genre> updateGenre(@PathVariable Long id, @RequestBody Genre genreDetails) {
        Genre existingGenre = genreService.getGenreById(id);

        if (existingGenre == null) {
            return ResponseEntity.notFound().build();
        }
        existingGenre.setGenreName(genreDetails.getGenreName());
        if (genreDetails.getGenresMovies() != null) {
            existingGenre.getGenresMovies().clear();
            existingGenre.getGenresMovies().addAll(genreDetails.getGenresMovies());
        }
        Genre updatedGenre = genreService.createGenre(existingGenre);

        return ResponseEntity.ok(updatedGenre);
    }
}