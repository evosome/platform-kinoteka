package org.example.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.modules.Feedback;
import org.example.modules.Producer;
import org.example.modules.Ticket;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class FilmDto {
    private long filmId;
    private String genre;
    private String country;
    private List<Producer> producers = new ArrayList<>();
    private Float mark;
    private String title;
    private int year;
    private int duration;
    private List<Ticket> sessions = new ArrayList<>();
    private List<Feedback> feedbacks = new ArrayList<>();
}
