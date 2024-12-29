package org.example.services;

import org.example.modules.*;
import org.example.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import jakarta.persistence.*;
import java.util.List;

@Service
public class TicketServices {
    private TicketRepository ticketRepository;
    @Autowired
    public TicketServices(TicketRepository ticketRepository){this.ticketRepository = ticketRepository;}
    public Page<Ticket> getAllTicket(int page, int size){
        return ticketRepository.findAll(PageRequest.of(page, size));
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
}
