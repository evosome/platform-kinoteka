package org.example.services;

import org.example.modules.Film;
import org.example.repositories.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class FilmServices {
    private FilmRepository filmRepository;
    @Autowired
    public FilmServices(FilmRepository filmRepository){this.filmRepository = filmRepository;}
    public List<Film> getAllFilm(){
        return filmRepository.findAll();
    }
    public Film createFilm(Film film){
        return filmRepository.save(film);
    }
    public Film getFilmById(Long bookId) {
        return filmRepository.findById(bookId).orElseThrow(() -> new EntityNotFoundException("Film not found with id: " + bookId));
    }
}
