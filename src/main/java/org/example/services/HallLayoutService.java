package org.example.services;

import org.example.modules.Genre;
import org.example.modules.HallLayout;
import org.example.repositories.HallLayoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import jakarta.persistence.*;
import java.util.List;

@Service
public class HallLayoutService {
    public static HallLayoutRepository hallLayoutRepository;
    @Autowired
    public HallLayoutService(HallLayoutRepository hallLayoutRepository) {
        HallLayoutService.hallLayoutRepository = hallLayoutRepository;
    }
    public Page<HallLayout> getAllHallLayout(int page, int size){
        return hallLayoutRepository.findAll(PageRequest.of(page, size));
    }
    public HallLayout createHallLayout(HallLayout layId){
        return hallLayoutRepository.save(layId);
    }
    public HallLayout getHallLayoutById(Long layId) {
        return hallLayoutRepository.findById(layId).orElseThrow(() -> new EntityNotFoundException("Lay not found with id: " + layId));
    }

}
