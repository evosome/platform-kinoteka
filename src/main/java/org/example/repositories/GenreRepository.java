package org.example.repositories;

import org.example.modules.Genre;
import org.example.modules.Halls;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long>, PagingAndSortingRepository<Genre,Long> {
}
