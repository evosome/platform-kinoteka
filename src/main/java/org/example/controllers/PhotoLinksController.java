package org.example.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.modules.Feedback;
import org.example.modules.Film;
import org.example.modules.PhotoLinks;
import org.example.services.FilmServices;
import org.example.services.PhotoLinksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api1/v1")
@Tag(name = "Photo", description = "The Photo API")
public class PhotoLinksController {
    public static PhotoLinksService photoLinksService;

    private final FilmServices filmServices;

    @Autowired
    public PhotoLinksController(PhotoLinksService photoLinksService, FilmServices filmServices) {
        this.filmServices = filmServices;
        PhotoLinksController.photoLinksService = photoLinksService;
    }

    @Operation(summary = "Gets all photo links", tags = "photo")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found the photo links",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = PhotoLinks.class)))
                    })
    })
    @GetMapping("/photoLinks")
    public Page<PhotoLinks> getAllPhotoLinks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return photoLinksService.getAllLinks(page, size);
    }

    @Operation(summary = "Create new photo link", tags = "photo")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Add new photo link",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PhotoLinks.class))
                    })
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/photoLinks/{id}")
    public PhotoLinks createPhotoLink(@PathVariable long id,@RequestBody PhotoLinks photoLink) {
        Film film = filmServices.getFilmById(id);
        film.addLinks(photoLink);
        photoLinksService.createLink(photoLink);
        return photoLink;
    }

    @Operation(summary = "Get photo link by id", tags = "photo")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Get photo link",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PhotoLinks.class))
                    })
    })
    @GetMapping("/photoLinks/{id}")
    public PhotoLinks getPhotoLinkById(@PathVariable long id) {
        return photoLinksService.getLinkById(id);
    }

    @Operation(summary = "Delete photo link by id", tags = "photo")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Delete photo link",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PhotoLinks.class))
                    })
    })
    @DeleteMapping("/photoLinks/{id},{filmId}")
    public void deletePhotoLinkById(@PathVariable Long id,@PathVariable long filmId) {
        Film film = filmServices.getFilmById(filmId);
        List<PhotoLinks> links = film.getLinks();
        links.removeIf(feedback -> feedback.getId() == id);
        photoLinksService.deleteLinkById(id);
    }
}
