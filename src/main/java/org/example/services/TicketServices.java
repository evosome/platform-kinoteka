package org.example.services;

import org.example.modules.Producer;
import org.example.modules.Ticket;
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
    public Ticket createCinemaSession(Ticket ticket){
        return ticketRepository.save(ticket);
    }
    public void deleteSessionById(long id){
        ticketRepository.deleteById(id);
    }
    public Ticket getCinemaSessionById(long cinemaSessionId) {
        return ticketRepository.findById(cinemaSessionId).orElseThrow(() -> new EntityNotFoundException("cinemaSession not found with id: " + cinemaSessionId));
    }
}
