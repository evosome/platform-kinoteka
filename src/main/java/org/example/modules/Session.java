package org.example.modules;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Session")
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long sessionId;
    private String date;
    private String cinemaType;
    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private Halls hallsFk;
    @OneToMany(mappedBy = "ticket",cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonIgnore
    private List<Ticket> tickets = new ArrayList<>();
    @OneToOne
    private Film filmFk;
    public void addTicket(Ticket ticket) {
        tickets.add(ticket);
        ticket.setTicket(this);
    }
}
