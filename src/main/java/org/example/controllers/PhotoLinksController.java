package org.example.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.modules.Film;
import org.example.modules.PhotoLinks;
import org.example.services.PhotoLinksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api1/v1")
@CrossOrigin
@Tag(name = "Photo", description = "The Photo API")
public class PhotoLinksController {
    public static PhotoLinksService photoLinksService;

    @Autowired
    public PhotoLinksController(PhotoLinksService photoLinksService) {
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
    @PostMapping("/photoLinks/{id}")
    public PhotoLinks createPhotoLink(@PathVariable long id,@RequestBody PhotoLinks photoLink) {
        Film film = FilmController.filmServices.getFilmById(id);
        film.addLinks(photoLink);
        photoLinksService.createLink(photoLink);
        return photoLink;
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
    @DeleteMapping("/photoLinks/{id}")
    public void deletePhotoLinkById(@PathVariable Long id) {
        photoLinksService.deleteLinkById(id);
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
}