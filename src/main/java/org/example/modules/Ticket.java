package org.example.modules;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Ticket")
public class  Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ticketId;

    @ManyToOne
    @JsonIgnore
    private MovieUser ticketFk;

    private Float price;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Seat> seats = new ArrayList<>();
    @ManyToOne
    private Session ticket;
    public void addSeat(Seat seat) {
        seats.add(seat);
        seat.setTicket(this);
    }
    public void removeSeat(Seat seat) {
        seats.remove(seat);
        seat.setTicket(null);
    }
}
