package com.evotek.aboutfilms.repositories;

import com.evotek.aboutfilms.modules.Cinemas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CinemasRepository extends JpaRepository<Cinemas,Long> {
}
