package org.example.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.dto.TicketDto;
import org.example.modules.MovieUser;
import org.example.modules.Session;
import org.example.modules.Ticket;
import org.example.services.TicketServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "Ticket", description = "The Ticket API")
@RestController
@RequestMapping("/api1/v1")
public class TicketController {
    private TicketServices ticketServices;
    public TicketController(TicketServices ticketServices){this.ticketServices = ticketServices;}
    @Operation(summary = "Gets all tickets", tags = "tickets")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the tickets",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Ticket.class))))
    })
    @GetMapping("/ticket")
    public Page<Ticket> getTickets(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ticketServices.getAllTicket(page, size);
    }
    @Operation(summary = "Create new ticket", tags = "tickets")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created ticket",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Ticket.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request - User or Session not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/ticket/{sessionId},{userId}")
    public Ticket createTicket(@PathVariable long sessionId, @PathVariable long userId,@RequestBody Ticket ticket){
        MovieUser movieUser = MovieUserController.userService.getUserById(userId);
        movieUser.addSession(ticket);
        Session session = SessionController.sessionService.getSessionById(sessionId);
        session.addTicket(ticket);
        ticketServices.createCinemaSession(ticket);
        return ticket;
    }
    @Operation(summary = "Delete ticket by id", tags = "tickets")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Ticket deleted"),
            @ApiResponse(responseCode = "404", description = "Ticket not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/ticket/{id}")
    public void deleteTicket(@PathVariable long id){
        MovieUser movieUser = MovieUserController.userService.getUserById(id);
        List<Ticket> tickets = movieUser.getMoviesWatched();
        tickets.removeIf(ticket -> ticket.getTicketId() == id);
        Session session = SessionController.sessionService.getSessionById(id);
        List<Ticket> tickets1 = session.getTickets();
        tickets1.removeIf(ticket -> ticket.getTicketId() == id);
        ticketServices.deleteSessionById(id);
    }
    @Operation(summary = "Get ticket by id", tags = "tickets")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found ticket with id",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Ticket.class))),
            @ApiResponse(responseCode = "404", description = "Ticket not found")
    })
    @GetMapping("/ticket/{id}")
    public Ticket getTicketById(@PathVariable long id){
        Ticket ticket = ticketServices.getCinemaSessionById(id);
        return ticket;
    }
}