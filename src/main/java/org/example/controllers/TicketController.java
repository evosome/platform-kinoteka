package org.example.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.dto.SeatsDto;
import org.example.dto.TicketDto;
import org.example.modules.*;
import org.example.services.TicketServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@Tag(name = "Ticket", description = "The Ticket API")
@RestController
@RequestMapping("/api1/v1")
public class TicketController {
    public static TicketServices ticketServices;
    public TicketController(TicketServices ticketServices){this.ticketServices = ticketServices;}
    @Operation(summary = "Gets all tickets", tags = "tickets")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the tickets",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Ticket.class))))
    })
    @GetMapping("/ticket")
    public List<TicketDto> getTickets() {
        List<Ticket> tickets = ticketServices.getAllTicket();
        List<TicketDto> ticketDtos = new ArrayList<>();
        for (Ticket ticket : tickets) {
            TicketDto ticketDto = convertTicket(ticket);
            ticketDtos.add(ticketDto);
        }
        return ticketDtos;
    }
    @Operation(summary = "Create new ticket", tags = "tickets")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created ticket",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Ticket.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request - User or Session not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping("/ticket/{sessionId},{userId}")
    public Ticket createTicket(@PathVariable long sessionId, @PathVariable long userId,@RequestBody Ticket ticket){
        MovieUser movieUser = MovieUserController.userService.getUserById(userId);
        movieUser.addSession(ticket);
        Session session = SessionController.sessionService.getSessionById(sessionId);
        session.addTicket(ticket);
        ticketServices.createTicket(sessionId,ticket);
        return ticket;
    }

    @Operation(summary = "Delete ticket by id", tags = "tickets")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Ticket deleted"),
            @ApiResponse(responseCode = "404", description = "Ticket not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @DeleteMapping("/ticket/{id}")
    public void deleteTicket(@PathVariable long id){
        MovieUser movieUser = MovieUserController.userService.getUserById(id);
        List<Ticket> tickets = movieUser.getMoviesWatched();
        tickets.removeIf(ticket -> ticket.getTicketId() == id);
        Session session = SessionController.sessionService.getSessionById(id);
        List<Ticket> tickets1 = session.getTickets();
        tickets1.removeIf(ticket -> ticket.getTicketId() == id);
        ticketServices.deleteTicketById(id);
    }
    @Operation(summary = "Get ticket by id", tags = "tickets")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found ticket with id",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Ticket.class))),
            @ApiResponse(responseCode = "404", description = "Ticket not found")
    })
    @GetMapping("/ticket/{id}")
    public TicketDto getTicketById(@PathVariable long id){
        return convertTicket(ticketServices.getTicketById(id));
    }
    @GetMapping("/hall/{hallId}/session/{sessionId}/occupied-seats")
    public List<Seat> getOccupiedSeats(@PathVariable Long hallId, @PathVariable Long sessionId) {
        return ticketServices.getOccupiedSeats(sessionId);
    }
    public static TicketDto convertTicket(Ticket ticket){

        TicketDto dto = new TicketDto();
        dto.setTicketId(ticket.getTicketId());
        Film film = ticket.getTicket().getFilmFromSession();
        if (film != null) {
            String title = film.getTitle();
            dto.setFilmTitle(title != null ? title : "Unknown");
            dto.setCoverFilm(film.getCover());
            dto.setFilmId(film.getFilmId());
        } else {
            dto.setFilmTitle("Unknown");
        }
        dto.setPrice(ticket.getPrice());
        dto.setMovieUser(ticket.getTicketFk());
        List<SeatsDto> seats = new ArrayList<>();
        for(Seat seat:ticket.getSeats()){
            SeatsDto seatDto = convertSeat(seat);
            seats.add(seatDto);
        }
        return dto;
    }
    public static SeatsDto convertSeat(Seat seat){
        SeatsDto dto = new SeatsDto();
        dto.setSeatId(seat.getSeatId());
        dto.setPlace(seat.getPlace());
        dto.setRow(seat.getRow());
        return dto;
    }
}