package org.example.controllers;

import org.example.modules.*;
import org.example.services.SessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "Sessions", description = "The Sessions API")
@RestController
@RequestMapping("/api1/v1")
@CrossOrigin
public class SessionController {
    public static SessionService sessionService;
    @Autowired
    public SessionController(SessionService sessionService){
        SessionController.sessionService = sessionService;
    }
    @Operation(summary = "Gets all sessions", tags = "sessions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the sessions",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Session.class))))
    })
    @GetMapping("/session")
    public Page<Session> getCinemaSession(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return sessionService.getAllSession(page, size);
    }
    @Operation(summary = "Create new session", tags = "sessions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created session",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Session.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request - Hall or Film not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping("/session/{hallId},{filmId}")
    public Session createSession(@PathVariable int hallId, @PathVariable int filmId, @RequestBody Session session){
        Halls hall = HallsController.hallsService.getHallsById(hallId);
        hall.addSessions(session);
        Film film = FilmController.filmServices.getFilmById((long) filmId);
        film.addSession(session);
        sessionService.createSession(session);
        return session;
    }
    @Operation(summary = "Get session by id", tags = "sessions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found session with id",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Session.class))),
            @ApiResponse(responseCode = "404", description = "Session not found")
    })
    @GetMapping("/sessions/{id}")
    public Session getSessionById(@PathVariable Long id){
        return sessionService.getSessionById(id);
    }
}