package org.example.services;

import org.example.modules.MovieUser;
import org.example.modules.Producer;
import org.example.repositories.ProducerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import jakarta.persistence.*;
import java.util.List;

@Service
public class ProducerService {
    public ProducerRepository producerRepository;
    @Autowired
    public ProducerService(ProducerRepository producerRepository){this.producerRepository = producerRepository;}
    public Page<Producer> getAllProducer(int page, int size){
        return producerRepository.findAll(PageRequest.of(page, size));
    }
    public Producer createProducer(Producer producer){
        return producerRepository.save(producer);
    }
    public Producer getProducerById(long producerId) {
        return producerRepository.findById(producerId).orElseThrow(() -> new EntityNotFoundException("Producer not found with id: " + producerId));
    }
}
