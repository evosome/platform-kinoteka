package com.evotek.film.modules;

import com.evotek.common.modules.Feedback;
import com.evotek.common.modules.Session;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Film")
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long filmId;
    @ManyToMany(mappedBy = "directorsMovies",cascade = CascadeType.ALL)
    private List<Producer> producers = new ArrayList<>();
    private Float mark;
    private String title;
    private int year;
    private int duration;
    @OneToMany(mappedBy = "filmFk",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Feedback> feedbacks = new ArrayList<>();
    @ManyToMany(mappedBy = "genresMovies",cascade = CascadeType.ALL)
    private List<Genre> genres = new ArrayList<>();
    @ManyToMany(mappedBy = "countryMovies",cascade = CascadeType.ALL)
    private List<Country> countries = new ArrayList<>();
    @OneToOne
    @JsonIgnore
    private Session sessionFk;

    public void addProducer(Film film) {
        List<Producer> producers = film.getProducers();
        for(Producer producer : producers) {
            List<Film> directors = producer.getDirectorsMovies();
            directors.add(this);
        }
    }
    public void addGenre(Film film) {
        List<Genre> genres = film.getGenres();
        for(Genre genre : genres) {
            List<Film> films = genre.getGenresMovies();
            films.add(this);
        }
    }

    public void addCountry(Film film) {
        List<Country> countries = film.getCountries();
        for(Country country : countries) {
            List<Film> films = country.getCountryMovies();
            films.add(this);
        }
    }
    public void addSession(Session session) {
       session.setFilmFk(this);
       sessionFk = session;

    }
    public void addFeedback(Feedback feedback) {
        feedbacks.add(feedback);
        feedback.setFilmFk(this);
    }
}
