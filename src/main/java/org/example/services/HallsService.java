package org.example.services;

import org.example.modules.Cinemas;
import org.example.modules.Halls;
import org.example.repositories.HallsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import jakarta.persistence.*;
import java.util.List;

@Service
public class HallsService {
    private HallsRepository hallsRepository;
    @Autowired
    public HallsService(HallsRepository hallsRepository){
        this.hallsRepository = hallsRepository;
    }
    public Page<Halls> getAllHalls(int page, int size){
        return hallsRepository.findAll(PageRequest.of(page, size));
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
