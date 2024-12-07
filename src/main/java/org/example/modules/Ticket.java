package org.example.modules;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
@Getter
@Setter
@Entity
@Table(name = "Ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ticketId;
    @ManyToOne
    @JsonIgnore
    private MovieUser ticketFk;
    private Integer row;
    private Integer place;
    @ManyToOne
    private Session ticket;
    private Float price;

}
