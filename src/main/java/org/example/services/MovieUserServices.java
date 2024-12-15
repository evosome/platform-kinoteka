package org.example.services;

import org.example.modules.HallLayout;
import org.example.modules.MovieUser;
import org.example.repositories.MovieUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import jakarta.persistence.*;
import java.util.List;

@Service
public class MovieUserServices {
    public MovieUserRepository movieUserRepository;
    @Autowired
    public MovieUserServices(MovieUserRepository movieUserRepository){this.movieUserRepository = movieUserRepository;}
    public Page<MovieUser> getAllUser(int page, int size){
        return movieUserRepository.findAll(PageRequest.of(page, size));
    }
    public MovieUser createUser(MovieUser movieUser){
        return movieUserRepository.save(movieUser);
    }
    public MovieUser getUserById(long shelfId) {
        return movieUserRepository.findById(shelfId).orElseThrow(() -> new EntityNotFoundException("User not found with id: " + shelfId));
    }
    public void deleteMovieUserById(long movieUserId) {movieUserRepository.deleteById(movieUserId); }
}
