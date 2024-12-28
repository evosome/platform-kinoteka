package org.example.services;

import org.example.modules.Film;
import org.example.modules.Genre;
import org.example.repositories.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import jakarta.persistence.*;
import java.util.List;

@Service
public class GenreService {
    private final GenreRepository genreRepository;

    @Autowired
    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }
    public Page<Genre> getAllGenres(int page, int size){
        return genreRepository.findAll(PageRequest.of(page, size));
    }
    public Genre createGenre(Genre genre) {
        return genreRepository.save(genre);
    }
    public void deleteGenre(Long id) {
        genreRepository.deleteById(id);
    }
    public Genre getGenreById(long genreId) {
        return genreRepository.findById(genreId).orElseThrow(() -> new EntityNotFoundException("Genre not found with id: " + genreId));
    }
}