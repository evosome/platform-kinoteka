package com.evotek.film.services;


import com.evotek.film.modules.Genre;
import com.evotek.film.repositories.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class GenreService {
    private final GenreRepository genreRepository;

    @Autowired
    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    public Genre createGenre(Genre genre) {
        return genreRepository.save(genre);
    }

    public Genre getGenreById(long genreId) {
        return genreRepository.findById(genreId).orElseThrow(() -> new EntityNotFoundException("Genre not found with id: " + genreId));
    }
}