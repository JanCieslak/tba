package io.github.jancieslak.tba.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScreeningRoomRow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Integer screeningRoomRow;

    @ManyToOne()
    @JoinColumn(name = "screening_room_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private ScreeningRoom screeningRoom;

    @OneToMany(mappedBy = "row", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Seat> seats = new HashSet<>();
}
