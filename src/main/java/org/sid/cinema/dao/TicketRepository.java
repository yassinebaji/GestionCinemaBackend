package org.sid.cinema.dao;

import org.sid.cinema.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@RepositoryRestResource
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByReserveIsNull();
    @Query("SELECT t FROM Ticket t  WHERE t.projectionFilm.id = :id AND t.reserve IS NULL")
    List<Ticket> findTest(@Param("id") long id);
}
