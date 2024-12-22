package org.example.modules;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "MovieUser")
public class MovieUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;
    private String name;
    private String surName;
    private String email;
    private String username;
    private String telephoneNumber;
    private String userImage;
    private String password;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "ticketFk",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Ticket> moviesWatched = new ArrayList<>();
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "movieUserFk",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Feedback> feedbacks = new ArrayList<>();
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    public MovieUser() {

    }
    public void addRole(MovieUser user) {
        Set<Role> roles = user.getRoles();
        for(Role role : roles) {
            Set<MovieUser> users = role.getUsers();
            users.add(this);
        }
    }
    public void addSession(Ticket session) {
        moviesWatched.add(session);
        session.setTicketFk(this);
    }
    public void addFeedback(Feedback feedback) {
        feedbacks.add(feedback);
        feedback.setMovieUserFk(this);
    }
    public MovieUser(String name,String surname,String email,String username,String telephoneNumber,String password){
        this.name = name;
        this.username = username;
        this.surName = surname;
        this.email = email;
        this.telephoneNumber = telephoneNumber;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
