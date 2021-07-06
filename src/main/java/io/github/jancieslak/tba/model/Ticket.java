package io.github.jancieslak.tba.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TicketType ticketType;

    @ManyToOne()
    @JoinColumn(name = "reservation_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    Reservation reservation;
}
