package org.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class SessionDto {
    private long sessionId;
    private String date;
    private List<TicketDto> tickets;
}
