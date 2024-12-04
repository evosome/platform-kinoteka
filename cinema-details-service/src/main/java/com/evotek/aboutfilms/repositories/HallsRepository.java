package com.evotek.aboutfilms.repositories;

import com.evotek.aboutfilms.modules.Halls;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HallsRepository extends JpaRepository<Halls,Long> {
}
