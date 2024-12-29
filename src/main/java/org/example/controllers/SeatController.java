package org.example.controllers;

import org.example.modules.MovieUser;
import org.example.modules.Seat;
import org.example.modules.Session;
import org.example.modules.Ticket;
import org.example.services.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api1/v1/seats")
public class SeatController {

    @Autowired
    private SeatService seatService;

    @GetMapping
    public List<Seat> getAllSeats() {
        return seatService.getAllSeats();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Seat> getSeatById(@PathVariable Long id) {
        return seatService.getSeatById(id)
                .map(seat -> ResponseEntity.ok().body(seat))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}")
    public ResponseEntity<Seat> createSeat(@RequestBody Seat seat,@PathVariable Long id) {
        Ticket ticket = TicketController.ticketServices.getTicketById(id);
        ticket.addSeat(seat);
        Seat createdSeat = seatService.createSeat(seat);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSeat);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Seat> updateSeat(@PathVariable Long id, @RequestBody Seat seat) {
        Seat updatedSeat = seatService.updateSeat(id, seat);
        return ResponseEntity.ok(updatedSeat);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeat(@PathVariable Long id) {
        Ticket ticket = TicketController.ticketServices.getTicketById(id);
        List<Seat> seats = ticket.getSeats();
        seats.removeIf(seat -> seat.getSeatId() == id);
        seatService.deleteSeat(id);
        return ResponseEntity.noContent().build();
    }
}
