package org.example.services;

import org.example.modules.Session;
import org.example.modules.Ticket;
import org.example.repositories.SessionRepository;
import org.example.specification.SessionSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import jakarta.persistence.*;
import java.util.List;
@Service
public class SessionService {
    public SessionRepository sessionRepository;
    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }
    public List<Session> getAllSession(String date, String cinemaType, Long hallId, Sort sort) {
        return sessionRepository.findAll(SessionSpecification.combineFilters(date, cinemaType, hallId), sort);
    }
    public Session createSession(Session session){
        return sessionRepository.save(session);
    }
    public void deleteSession(Long id) {
        sessionRepository.deleteById(id);
    }
    public Session getSessionById(long cinemaSessionId) {
        return sessionRepository.findById(cinemaSessionId).orElseThrow(() -> new EntityNotFoundException("cinemaSession not found with id: " + cinemaSessionId));
    }
}
