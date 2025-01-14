package org.example.repositories;

import org.example.modules.HallLayout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HallLayoutRepository extends JpaRepository<HallLayout, Long> {
}
