package org.example.modules;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@Entity
@Table(name = "Producer")
public class Producer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long producerId;
    private String name;
    private String surname;
    @ManyToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Film> directorsMovies = new ArrayList<>();

}
