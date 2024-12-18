package org.example.modules;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Film")
public class Film {
    private static final Logger log = LoggerFactory.getLogger(Film.class);
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long filmId;
    @ManyToMany(mappedBy = "directorsMovies",cascade = CascadeType.ALL)
    private List<Producer> producers = new ArrayList<>();
    private Float mark;
    private String title;
    private int year;
    private int duration;
    public String cover;
    @OneToMany(mappedBy = "filmFk",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Feedback> feedbacks = new ArrayList<>();
    @ManyToMany(mappedBy = "genresMovies",cascade = CascadeType.ALL)
    private List<Genre> genres = new ArrayList<>();
    @ManyToMany(mappedBy = "countryMovies",cascade = CascadeType.ALL)
    private List<Country> countries = new ArrayList<>();
    @OneToMany
    private List<Session> sessions = new ArrayList<>();
    @OneToMany
    private List<PhotoLinks> links = new ArrayList<>();

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
      sessions.add(session);
      session.setFilmFk(this);

    }
    public void addFeedback(Feedback feedback) {
        feedbacks.add(feedback);
        feedback.setFilmFk(this);
    }
    public void addLinks(PhotoLinks link) {
        links.add(link);
        link.setFilm(this);
    }
}
