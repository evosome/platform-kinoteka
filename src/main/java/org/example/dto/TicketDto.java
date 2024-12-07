package org.example.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.modules.Film;
import org.example.modules.MovieUser;
import org.example.modules.Session;

@Getter
@Setter
public class TicketDto {
    private long sessionId;
    private MovieUser sessionFk;
    private Integer row;
    private Integer place;
    private Film film;
    private Float price;
    private Session ticket;
}
