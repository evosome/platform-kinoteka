package org.example.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.modules.Film;
import org.example.modules.MovieUser;
import org.example.modules.Seat;
import org.example.modules.Session;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TicketDto {
    private long ticketId;
    private Float price;
    private String filmTitle;
    private List<Seat> seats = new ArrayList<>();
    private MovieUser movieUser;
    private String coverFilm;
    private long filmId;
}
