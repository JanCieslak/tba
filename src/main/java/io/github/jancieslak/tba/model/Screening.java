package io.github.jancieslak.tba.model;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Screening {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private LocalDateTime fromDateTime;

    @NotNull
    private LocalDateTime toDateTime;

    @ManyToOne()
    @JoinColumn(name = "movie_id")
    @EqualsAndHashCode.Exclude
    private Movie movie;

    @ManyToOne()
    @JoinColumn(name = "screening_room_id")
    @EqualsAndHashCode.Exclude
    private ScreeningRoom screeningRoom;
}
