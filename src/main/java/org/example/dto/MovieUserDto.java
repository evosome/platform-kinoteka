package org.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MovieUserDto {
    private long userId;
    private String name;
    private String surName;
    private String email;
    private long telephoneNumber;
    private String password;
    private String login;
    private List<TicketDto> moviesWatched = new ArrayList<>();
}
