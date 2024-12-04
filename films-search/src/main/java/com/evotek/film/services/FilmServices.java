package com.evotek.film.services;

import com.evotek.film.modules.Film;
import com.evotek.film.repositories.FilmRepository;
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
