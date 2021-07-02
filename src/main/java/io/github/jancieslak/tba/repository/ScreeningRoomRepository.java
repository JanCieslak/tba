package io.github.jancieslak.tba.repository;

import io.github.jancieslak.tba.model.ScreeningRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScreeningRoomRepository extends JpaRepository<ScreeningRoom, Long> {
    @Query("from ScreeningRoom sr join fetch sr.screenings s join fetch sr.rows r join fetch r.seats where s.id = :id")
    Optional<ScreeningRoom> findScreeningRoomByScreeningId(long id);
}
