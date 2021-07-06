package io.github.jancieslak.tba.repository;

import io.github.jancieslak.tba.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    @Query("select s.id from Seat s join s.reservedSeats rs join rs.reservation r join r.screening scr where scr.id = :id")
    List<Long> findIdsByReservedSeatsAndScreeningId(@Param("id") long screeningId);
}
