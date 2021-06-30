package io.github.jancieslak.tba.model;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Integer seatCol;

    @NotNull
    private Boolean available;

    @ManyToOne()
    @JoinColumn(name = "screening_room_row_id")
    @EqualsAndHashCode.Exclude
    private ScreeningRoomRow row;

    @OneToMany(mappedBy = "seat", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @EqualsAndHashCode.Exclude
    private Set<ReservedSeat> reservedSeats = new HashSet<>();
}
