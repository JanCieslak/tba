package io.github.jancieslak.tba.model;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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

    @OneToMany(mappedBy = "screening")
    @Builder.Default
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Reservation> reservations = new HashSet<>();

    @ManyToOne()
    @JoinColumn(name = "movie_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Movie movie;

    @ManyToOne()
    @JoinColumn(name = "screening_room_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private ScreeningRoom screeningRoom;
}
