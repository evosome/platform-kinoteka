package com.evotek.kinoteka.services;

import com.evotek.kinoteka.modules.Ticket;
import com.evotek.kinoteka.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class TicketServices {
    private TicketRepository ticketRepository;
    @Autowired
    public TicketServices(TicketRepository ticketRepository){this.ticketRepository = ticketRepository;}
    public List<Ticket> getAllCinemaSession(){
        return ticketRepository.findAll();
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
