package org.example.services;

import org.example.modules.Cinemas;
import org.example.repositories.CinemasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import jakarta.persistence.*;
import java.util.List;

@Service
public class CinemasService {
    private CinemasRepository cinemasRepository;
    @Autowired
    public CinemasService(CinemasRepository cinemasRepository){
        this.cinemasRepository = cinemasRepository;
    }
    public Page<Cinemas> getAllCinemas(int page, int size){
        return cinemasRepository.findAll(PageRequest.of(page, size));
    }
    public Cinemas createCinemas(Cinemas cinemas){
        return cinemasRepository.save(cinemas);
    }
    public void deleteCinemasById(long id){
        cinemasRepository.deleteById(id);
    }
    public Cinemas getCinemasById(long cinemasId) {
        return cinemasRepository.findById(cinemasId).orElseThrow(() -> new EntityNotFoundException("cinemaSession not found with id: " + cinemasId));
    }
}
