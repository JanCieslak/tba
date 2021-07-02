package io.github.jancieslak.tba.repository;

import io.github.jancieslak.tba.model.Screening;
import io.github.jancieslak.tba.model.ScreeningRoom;
import io.github.jancieslak.tba.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening, Long> {
    @Query(nativeQuery = true, value = "select * from Screening s inner join Movie m on s.movie_id = m.id where s.from_date_time between :beginFrom and :beginTo")
    List<Screening> findByDateTimeInterval(@Param("beginFrom") LocalDateTime from, @Param("beginTo") LocalDateTime to);

    Optional<Screening> findByFromDateTimeAndMovieTitle(LocalDateTime from, String title);

//    @Query("from Screening s join s.movie m where s.fromDateTime = :from and m.title = :title")
//    Optional<Screening> findByFromDateTimeAndMovieTitle(LocalDateTime from, String title);
}
