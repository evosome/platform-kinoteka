package org.example.services;

import org.example.modules.Halls;
import org.example.repositories.HallsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class HallsService {
    private HallsRepository hallsRepository;
    @Autowired
    public HallsService(HallsRepository hallsRepository){
        this.hallsRepository = hallsRepository;
    }
    public List<Halls> getAllHalls(){
        return hallsRepository.findAll();
    }
    public Halls createHalls(Halls hall){
        return hallsRepository.save(hall);
    }
    public void deleteHallsById(long id){
        hallsRepository.deleteById(id);
    }
    public Halls getHallsById(long hallsId) {
        return hallsRepository.findById(hallsId).orElseThrow(() -> new EntityNotFoundException("cinemaSession not found with id: " + hallsId));
    }
}
