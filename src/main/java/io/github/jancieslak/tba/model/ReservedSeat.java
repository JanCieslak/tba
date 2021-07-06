package io.github.jancieslak.tba.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservedSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "seat_id")
    @EqualsAndHashCode.Exclude
    private Seat seat;

    @ManyToOne()
    @JoinColumn(name = "reservation_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Reservation reservation;
}
