package io.github.jancieslak.tba.repository;

import io.github.jancieslak.tba.model.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening, Long> {
    @Query(nativeQuery = true, value = "select * from Screening s inner join Movie m on s.movie_id = m.id where s.from_date_time between :beginFrom and :beginTo")
    List<Screening> findByDateTimeInterval(@Param("beginFrom") LocalDateTime from, @Param("beginTo") LocalDateTime to);
}
