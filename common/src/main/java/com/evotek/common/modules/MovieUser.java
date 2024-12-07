package com.evotek.common.modules;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
@Getter
@Setter
@Entity
@Table(name = "MovieUser")
public class MovieUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;
    private String name;
    private String surName;
    private String email;
    private String login;
    private long telephoneNumber;
    private String password;
    @OneToMany(mappedBy = "ticketFk",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Ticket> moviesWatched = new ArrayList<>();
    @OneToMany(mappedBy = "movieUserFk",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Feedback> feedbacks = new ArrayList<>();
    public void addSession(Ticket session) {
        moviesWatched.add(session);
        session.setTicketFk(this);
    }
    public void removeSession(Ticket session) {
        moviesWatched.remove(session);
        session.setTicketFk(null);
    }
    public void addFeedback(Feedback feedback) {
        feedbacks.add(feedback);
        feedback.setMovieUserFk(this);
    }

}
