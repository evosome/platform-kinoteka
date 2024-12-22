package org.example.repositories;

import org.example.modules.Feedback;
import org.example.modules.Film;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback,Long>, PagingAndSortingRepository<Feedback,Long> {
    Page<Feedback> getByFilmFk(Film filmFk, PageRequest pageable);
}