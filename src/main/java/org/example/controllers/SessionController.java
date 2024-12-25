package org.example.controllers;

import lombok.AllArgsConstructor;
import org.example.modules.*;
import org.example.services.FilmServices;
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
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "Sessions", description = "The Sessions API")
@RestController
@RequestMapping("/api1/v1")
public class SessionController {
    public static SessionService sessionService;

    private final FilmServices filmServices;

    @Autowired
    public SessionController(SessionService sessionService, FilmServices filmServices){
        this.filmServices = filmServices;
        SessionController.sessionService = sessionService;
    }

    @Operation(summary = "Gets all sessions", tags = "sessions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the sessions",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Session.class))))
    })
    @GetMapping("/session")
    public List<Session> getCinemaSession(
            @RequestParam(required = false) String date,
            @RequestParam(required = false) String cinemaType,
            @RequestParam(required = false) Long hallId,
            @RequestParam(defaultValue = "asc") String sortOrder) {
        Sort sort = sortOrder.equalsIgnoreCase("desc") ? Sort.by("date").descending() : Sort.by("date").ascending();
        return sessionService.getAllSession(date, cinemaType, hallId, sort);
    }
    @Operation(summary = "Create new session", tags = "sessions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created session",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Session.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request - Hall or Film not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/session/{hallId},{filmId}")
    public Session createSession(@PathVariable int hallId, @PathVariable int filmId, @RequestBody Session session){
        Halls hall = HallsController.hallsService.getHallsById(hallId);
        hall.addSessions(session);
        Film film = filmServices.getFilmById((long) filmId);
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
    @Operation(summary = "Update session", tags = "sessions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated session",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Session.class))),
            @ApiResponse(responseCode = "404", description = "Session not found"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Invalid data"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/session/{id}")
    public ResponseEntity<Session> updateSession(@PathVariable Long id, @RequestBody Session sessionDetails) {
        Session existingSession = sessionService.getSessionById(id);

        if (existingSession == null) {
            return ResponseEntity.notFound().build();
        }
        existingSession.setDate(sessionDetails.getDate());
        existingSession.setCinemaType(sessionDetails.getCinemaType());
        existingSession.setHallsFk(sessionDetails.getHallsFk());
        existingSession.setFilmFk(sessionDetails.getFilmFk());
        existingSession.setPrice(sessionDetails.getPrice());

        Session updatedSession = sessionService.createSession(existingSession);

        return ResponseEntity.ok(updatedSession);
    }
    @Operation(summary = "Delete session by id", tags = "sessions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Session deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Session not found")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/session/{id}")
    public void deleteSession(@PathVariable Long id) {
        Session session = sessionService.getSessionById(id);
        session.setHallsFk(null);
        session.setFilmFk(null);
        sessionService.deleteSession(id);
    }
}