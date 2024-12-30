package org.example.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.modules.Film;
import org.example.modules.MovieUser;
import org.example.modules.Session;

@Getter
@Setter
public class TicketDto {
    private long ticketId;
    private Float price;
}
