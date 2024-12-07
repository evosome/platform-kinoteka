package com.evotek.common.modules;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
@Getter
@Setter
@Entity
@Table(name = "Genre")
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long genreId;
    private String genreName;
    @ManyToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Film> genresMovies = new ArrayList<>();
}
