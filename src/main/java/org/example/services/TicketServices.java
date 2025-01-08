package org.example.services;

import org.example.controllers.SessionController;
import org.example.controllers.TicketController;
import org.example.dto.TicketDto;
import org.example.modules.*;
import org.example.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketServices {
    private TicketRepository ticketRepository;
    @Autowired
    public TicketServices(TicketRepository ticketRepository){this.ticketRepository = ticketRepository;}
    public List<Ticket> getAllTicket(){
        return ticketRepository.findAll();
    }
    public Ticket createTicket(Long sessionId,Ticket ticket){
        Session session = SessionService.sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Такого сеанса нет"));
        for (Seat seat : ticket.getSeats()) {
            for (Ticket existingTicket : session.getTickets()) {
                for (Seat existingSeat : existingTicket.getSeats()) {
                    if (existingSeat.getRow().equals(seat.getRow()) && existingSeat.getPlace().equals(seat.getPlace())) {
                        throw new RuntimeException("Место " + seat.getRow() + "-" + seat.getPlace() + " уже забронировано");
                    }
                }
            }
        }
        return ticketRepository.save(ticket);
    }
    public void deleteTicketById(long id){
        ticketRepository.deleteById(id);
    }
    public Ticket getTicketById(long cinemaSessionId) {
        return ticketRepository.findById(cinemaSessionId).orElseThrow(() -> new EntityNotFoundException("cinemaSession not found with id: " + cinemaSessionId));
    }
    public List<Seat> getOccupiedSeats(long sessionId) {
        Session session = SessionController.sessionService.getSessionById(sessionId);
        List<Ticket> tickets = session.getTickets();
        List<TicketDto> dto = new ArrayList<>();
        for(Ticket ticket : tickets){
            dto.add(TicketController.convertTicket(ticket));
        }
        return dto.stream()
                .flatMap(ticket -> ticket.getSeats().stream())
                .collect(Collectors.toList());
    }
}
