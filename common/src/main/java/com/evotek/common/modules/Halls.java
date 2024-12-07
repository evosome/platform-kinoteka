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
@Table(name = "Halls")
public class Halls {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long hallsId;
    private int quantityRow;
    private int quantityPlace;
    private int maketId;
    @OneToMany
    private List<Session> sessions = new ArrayList<>();
    @ManyToOne
    @JsonIgnore
    private Cinemas cinemasFk;
    @OneToOne
    private HallLayout hallLayout;

    public void addSessions(Session session) {
        sessions.add(session);
        session.setHallsFk(this);
    }
}
