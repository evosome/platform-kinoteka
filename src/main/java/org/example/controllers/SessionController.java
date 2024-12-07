package org.example.controllers;

import org.example.modules.*;
import org.example.services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api1/v1")
public class SessionController {
    public static SessionService sessionService;
    @Autowired
    public SessionController(SessionService sessionService){
        SessionController.sessionService = sessionService;
    }
    @GetMapping("/session")
    public List<Session> getCinemaSession(){
        return sessionService.getAllSession();
    }
    @PostMapping("/session/{hallId},{filmId}")
    public Session createSession(@PathVariable int hallId, @PathVariable int filmId, @RequestBody Session session){
        Halls hall = HallsController.hallsService.getHallsById(hallId);
        hall.addSessions(session);
        Film film = FilmController.filmServices.getFilmById((long) filmId);
        film.addSession(session);
        sessionService.createSession(session);
        return session;
    }
    @GetMapping("/sessions/{id}")
    public Session getSessionById(@PathVariable Long id){
        return sessionService.getSessionById(id);
    }
}
