package org.example.repositories;

import org.example.modules.Halls;
import org.example.modules.MovieUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieUserRepository extends JpaRepository<MovieUser,Long>, PagingAndSortingRepository<MovieUser,Long> {
}
