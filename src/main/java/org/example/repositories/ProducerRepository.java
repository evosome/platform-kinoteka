package org.example.repositories;

import org.example.modules.Halls;
import org.example.modules.Producer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProducerRepository extends JpaRepository<Producer,Long>, PagingAndSortingRepository<Producer,Long> {
}
