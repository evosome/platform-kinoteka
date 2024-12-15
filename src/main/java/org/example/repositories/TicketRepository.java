package org.example.repositories;

import org.example.modules.Halls;
import org.example.modules.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface TicketRepository extends JpaRepository<Ticket,Long>, PagingAndSortingRepository<Ticket,Long> {
}
