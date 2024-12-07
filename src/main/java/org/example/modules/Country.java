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
@Table(name = "Country")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long countryId;
    private String countryName;
    private String countryCode;
    private String linkPhoto;
    @ManyToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Film> countryMovies = new ArrayList<>();
}
