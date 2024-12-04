package com.evotek.user.repositories;

import com.evotek.user.modules.MovieUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieUserRepository extends JpaRepository<MovieUser,Long> {
}
