package com.evotek.feedback.controllers;

import com.evotek.feedback.modules.Feedback;
import com.evotek.feedback.services.FeedbackService;
import com.evotek.film.controllers.FilmController;
import com.evotek.film.modules.Film;
import com.evotek.user.controllers.MovieUserController;
import com.evotek.user.modules.MovieUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "Feedback", description = "The Feedback API")
@RestController
@RequestMapping("/api1/v1")
public class FeedbackController {
    private FeedbackService feedbackServices;
    @Autowired
    public FeedbackController(FeedbackService feedbackServices){this.feedbackServices = feedbackServices;}
    @Operation(summary = "Gets all feedback", tags = "feedback")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the feedback",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Feedback.class))))
    })
    @GetMapping("/feedback")
    public List<Feedback> getCinemaSession(){
        return feedbackServices.getAllFeedback();
    }
    @Operation(summary = "Create new feedback", tags = "feedback")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Add new feedback",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Feedback.class)))
    })
    @PostMapping("/feedback/{userId},{filmId}")
    public Feedback createFeedback(@PathVariable long userId,@PathVariable long filmId,@RequestBody Feedback feedback){
        Film film = FilmController.filmServices.getFilmById(filmId);
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
}
