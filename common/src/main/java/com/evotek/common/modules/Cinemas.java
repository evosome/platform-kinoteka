package com.evotek.common.modules;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Cinemas")
public class Cinemas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long cinemasId;
    private String address;
    private String name;
    private String type;
    @OneToMany(mappedBy = "cinemasFk",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Halls> halls = new ArrayList<>();

    public void addHalls(Halls hall) {
        halls.add(hall);
        hall.setCinemasFk(this);
    }
}
