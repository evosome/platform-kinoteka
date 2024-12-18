package org.example.repositories;

import org.example.modules.Halls;
import org.example.modules.MovieUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieUserRepository extends JpaRepository<MovieUser,Long>, PagingAndSortingRepository<MovieUser,Long> {
    Optional<MovieUser> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
