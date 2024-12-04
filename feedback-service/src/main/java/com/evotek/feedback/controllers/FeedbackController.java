package com.evotek.feedback.controllers;

import com.evotek.feedback.modules.Feedback;
import com.evotek.feedback.services.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api1/v1")
public class FeedbackController {
    private FeedbackService feedbackServices;
    @Autowired
    public FeedbackController(FeedbackService feedbackServices){this.feedbackServices = feedbackServices;}
    @GetMapping("/feedback")
    public List<Feedback> getCinemaSession(){
        return feedbackServices.getAllFeedback();
    }
    @PostMapping("/feedback/{userId},{filmId}")
    public Feedback createFeedback(@PathVariable long userId,@PathVariable long filmId,@RequestBody Feedback feedback){
        Film film = FilmController.filmServices.getFilmById(filmId);
        film.addFeedback(feedback);
        MovieUser movieUser = MovieUserController.userService.getUserById(userId);
        movieUser.addFeedback(feedback);
        feedbackServices.createFeedback(feedback);
        return feedback;
    }
    @GetMapping("/feedbacks/{id}")
    public Feedback getFeedbackById(@PathVariable Long id){
        return feedbackServices.getFeedbackById(id);
    }
}
