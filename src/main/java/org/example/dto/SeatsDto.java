package org.example.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.example.modules.Ticket;
@Getter
@Setter
public class SeatsDto {
    private long seatId;
    private Integer row;
    private Integer place;
}
