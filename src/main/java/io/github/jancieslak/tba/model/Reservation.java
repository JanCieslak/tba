package io.github.jancieslak.tba.model;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank(message = "Reservation: firstname should not be blank")
    private String firstname;

    @NotNull
    @NotBlank(message = "Reservation: lastname should not be blank")
    private String lastname;

    @ManyToOne()
    @JoinColumn(name = "screening_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Screening screening;

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<ReservedSeat> reservedSeats = new HashSet<>();

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Ticket> tickets = new HashSet<>();
}
