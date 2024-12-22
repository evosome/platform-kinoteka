package org.example.services;

import org.example.modules.Cinemas;
import org.example.modules.Film;
import org.example.repositories.FilmRepository;
import org.example.specification.FilmSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import jakarta.persistence.*;
import java.util.List;

@Service
public class FilmServices {
    private FilmRepository filmRepository;
    @Autowired
    public FilmServices(FilmRepository filmRepository){this.filmRepository = filmRepository;}
    public Page<Film> getAllFilm(int page, int size, List<String> genreNames, List<String> countryNames, String title, Integer ageRestriction, Long hallId) {
        return filmRepository.findAll(FilmSpecification.combineFilters(genreNames, countryNames, title, ageRestriction, hallId), PageRequest.of(page, size));
    }
    public Film createFilm(Film film){
        return filmRepository.save(film);
    }
    public Film getFilmById(Long bookId) {
        return filmRepository.findById(bookId).orElseThrow(() -> new EntityNotFoundException("Film not found with id: " + bookId));
    }
    public void deleteFilmById(long filmId){
        filmRepository.deleteById(filmId);
    }
}
