package com.evotek.kinoteka.controllers;


import com.evotek.kinoteka.modules.Ticket;
import com.evotek.kinoteka.services.TicketServices;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api1/v1")
public class TicketController {
    private TicketServices ticketServices;
    public TicketController(TicketServices ticketServices){this.ticketServices = ticketServices;}
    @GetMapping("/ticket")
    public List<Ticket> getTickets(){
        return ticketServices.getAllCinemaSession();
    }
    @PostMapping("/ticket/{sessionId},{userId}")
    public Ticket createTicket(@PathVariable long sessionId, @PathVariable long userId,@RequestBody Ticket ticket){
        MovieUser movieUser = MovieUserController.userService.getUserById(userId);
        movieUser.addSession(ticket);
        Session session = SessionController.sessionService.getSessionById(sessionId);
        session.addTicket(ticket);
        ticketServices.createCinemaSession(ticket);
        return ticket;
    }
    @DeleteMapping("/ticket/{id}")
    public void deleteTicket(@PathVariable long id){
        MovieUser movieUser = MovieUserController.userService.getUserById(id);
        movieUser.removeSession(ticketServices.getCinemaSessionById(id));
        Session session = SessionController.sessionService.getSessionById(id);
        session.removeSession(ticketServices.getCinemaSessionById(id));
        ticketServices.deleteSessionById(id);
    }
    @GetMapping("/ticket/{id}")
    public Ticket getTicketById(@PathVariable long id){
        Ticket ticket = ticketServices.getCinemaSessionById(id);
        return ticket;
        //return cinemaSessionServices.getCinemaSessionById(id);
    }
//    public Ticket CinemaSessionDtoToCinemaSession(TicketDto ticketDto) {
//        Ticket ticket = new Ticket();
//        ticket.setTicketId(ticketDto.getSessionId());
//        ticket.setTicketFk(ticketDto.getSessionFk());
//        ticket.setRow(ticketDto.getRow());
//        ticket.setPlace(ticketDto.getPlace());
//        return ticket;
//    }
//    public static TicketDto TicketToDto(Ticket ticket) {
//        TicketDto ticketDto = new TicketDto();
//        ticketDto.setSessionId(ticket.getTicketId());
//        ticketDto.setSessionFk(ticket.getTicketFk());
//        ticketDto.setRow(ticket.getRow());
//        ticketDto.setPlace(ticket.getPlace());
//        return ticketDto;
//    }


}
