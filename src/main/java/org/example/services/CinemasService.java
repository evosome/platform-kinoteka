package org.example.services;

import org.example.modules.Cinemas;
import org.example.repositories.CinemasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class CinemasService {
    private CinemasRepository cinemasRepository;
    @Autowired
    public CinemasService(CinemasRepository cinemasRepository){
        this.cinemasRepository = cinemasRepository;
    }
    public List<Cinemas> getAllCinemas(){
        return cinemasRepository.findAll();
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
