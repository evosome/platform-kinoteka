package org.example.repositories;
import org.example.modules.Halls;
import org.example.modules.PhotoLinks;
import org.example.modules.Producer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoLinksRepository extends JpaRepository<PhotoLinks,Long>, PagingAndSortingRepository<PhotoLinks,Long>{
}
