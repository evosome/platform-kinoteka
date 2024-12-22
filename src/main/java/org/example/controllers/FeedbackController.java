package org.example.controllers;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.example.modules.Feedback;
import org.example.modules.Film;
import org.example.modules.MovieUser;
import org.example.modules.Session;
import org.example.services.FeedbackService;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.services.FilmServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "Feedback", description = "The Feedback API")
@RestController
@RequestMapping("/api1/v1")
@CrossOrigin(origins = "*", maxAge = 3600)
@AllArgsConstructor
public class FeedbackController {

    private final FilmServices filmServices;

    private final FeedbackService feedbackServices;

    @Operation(summary = "Gets all feedback", tags = "feedback")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the feedback",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Feedback.class))))
    })
    @GetMapping("/feedback")
    public Page<Feedback> getCinemaSession(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {
            return feedbackServices.getAllFeedback(page, size);
    }
    @Operation(summary = "Create new feedback", tags = "feedback")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Add new feedback",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Feedback.class)))
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/feedback/{userId},{filmId}")
    public Feedback createFeedback(@PathVariable long userId,@PathVariable long filmId,@RequestBody Feedback feedback){
        Film film = filmServices.getFilmById(filmId);
        film.addFeedback(feedback);
        MovieUser movieUser = MovieUserController.userService.getUserById(userId);
        movieUser.addFeedback(feedback);
        feedbackServices.createFeedback(feedback);
        return feedback;
    }
    @Operation(summary = "Get feedback by id", tags = "feedback")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found feedback with id",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Feedback.class)))
    })
    @GetMapping("/feedbacks/{id}")
    public Feedback getFeedbackById(@PathVariable Long id){
        return feedbackServices.getFeedbackById(id);
    }
    @Operation(summary = "Delete feedback by id", tags = "feedback")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete feedback with id",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Feedback.class)))
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/feedback/{userId},{filmId},{feedbackId}")
    public void deleteTicket(@PathVariable long userId,@PathVariable long filmId,@PathVariable long feedbackId){
        Film film = filmServices.getFilmById(filmId);
        List<Feedback> feedbacks = film.getFeedbacks();
        feedbacks.removeIf(feedback -> feedback.getFeedbackId() == feedbackId);
        MovieUser movieUser = MovieUserController.userService.getUserById(userId);
        List<Feedback> feedbacks1 = movieUser.getFeedbacks();
        feedbacks1.removeIf(feedback -> feedback.getFeedbackId() == feedbackId);
        feedbackServices.deleteFeedbackById(feedbackId);
    }
}