package org.example.repositories;

import org.example.modules.Halls;
import org.example.modules.Role;
import org.example.modules.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long>, PagingAndSortingRepository<Session, Long>, JpaSpecificationExecutor<Session> {
}
