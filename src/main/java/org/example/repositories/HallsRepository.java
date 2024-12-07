package org.example.repositories;

import org.example.modules.Halls;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HallsRepository extends JpaRepository<Halls,Long> {
}
