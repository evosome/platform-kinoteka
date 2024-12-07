package org.example.services;

import org.example.modules.HallLayout;
import org.example.repositories.HallLayoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class HallLayoutService {
    public static HallLayoutRepository hallLayoutRepository;
    @Autowired
    public HallLayoutService(HallLayoutRepository hallLayoutRepository) {
        HallLayoutService.hallLayoutRepository = hallLayoutRepository;
    }
    public List<HallLayout> getAllHallLayout(){
        return hallLayoutRepository.findAll();
    }
    public HallLayout createHallLayout(HallLayout layId){
        return hallLayoutRepository.save(layId);
    }
    public HallLayout getHallLayoutById(Long layId) {
        return hallLayoutRepository.findById(layId).orElseThrow(() -> new EntityNotFoundException("Lay not found with id: " + layId));
    }

}
