package com.evotek.aboutfilms.modules;

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
@Table(name = "Halls")
public class Halls extends com.evotek.common.modules.Halls {
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