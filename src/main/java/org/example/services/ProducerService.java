package org.example.services;

import org.example.modules.Producer;
import org.example.repositories.ProducerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ProducerService {
    public ProducerRepository producerRepository;
    @Autowired
    public ProducerService(ProducerRepository producerRepository){this.producerRepository = producerRepository;}
    public List<Producer> getAllProducer(){
        return producerRepository.findAll();
    }
    public Producer createProducer(Producer producer){
        return producerRepository.save(producer);
    }
    public Producer getProducerById(long producerId) {
        return producerRepository.findById(producerId).orElseThrow(() -> new EntityNotFoundException("Producer not found with id: " + producerId));
    }
}
