package org.example.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.modules.Film;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ProducerDto {
    private long id;
    private String name;
    private String surname;
    private List<Film> directorsMovies = new ArrayList<>();
}
