package com.evotek.service.services;

import com.evotek.service.modules.Session;
import com.evotek.service.repositories.SessionRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class SessionService {
    public SessionRepository sessionRepository;
    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }
    public List<Session> getAllSession(){
        return sessionRepository.findAll();
    }
    public Session createSession(Session session){
        return sessionRepository.save(session);
    }
    public void deleteSessionById(long id){
        sessionRepository.deleteById(id);
    }
    public Session getSessionById(long cinemaSessionId) {
        return sessionRepository.findById(cinemaSessionId).orElseThrow(() -> new EntityNotFoundException("cinemaSession not found with id: " + cinemaSessionId));
    }
}
