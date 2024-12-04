package com.evotek.film.controllers;

import com.evotek.film.modules.Film;
import com.evotek.film.services.FilmServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api1/v1")
public class FilmController {
    public static FilmServices filmServices;
    @Autowired
    public FilmController(FilmServices filmServices){
        FilmController.filmServices = filmServices;
    }
    @GetMapping("/film")
    public List<Film> getFilm(){

        return filmServices.getAllFilm();
    }
    @PostMapping("/film")
    public Film createFilm(@RequestBody Film film){
        film.addProducer(film);
        film.addCountry(film);
        film.addGenre(film);
        filmServices.createFilm(film);
        return film;
    }
    @GetMapping("/film/{id}")
    public Film getFilmById(@PathVariable Long id){
//        Film film = filmServices.getFilmById(id);
//        return FilmToFilmDto(film);
        return filmServices.getFilmById(id);
    }
}
