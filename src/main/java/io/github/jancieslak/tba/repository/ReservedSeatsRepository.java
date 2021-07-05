package io.github.jancieslak.tba.repository;

import io.github.jancieslak.tba.model.ReservedSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ReservedSeatsRepository extends JpaRepository<ReservedSeat, Long> {
    Set<ReservedSeat> findAllByReservationScreeningId(long screeningId);
}
