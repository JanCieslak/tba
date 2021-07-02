package io.github.jancieslak.tba.services;

import io.github.jancieslak.tba.dto.PostReserveScreeningRequestModel;
import io.github.jancieslak.tba.dto.PostReserveScreeningResponseModel;
import io.github.jancieslak.tba.model.Reservation;
import io.github.jancieslak.tba.model.ReservedSeat;
import io.github.jancieslak.tba.model.Ticket;
import io.github.jancieslak.tba.repository.ReservationRepository;
import io.github.jancieslak.tba.repository.ScreeningRepository;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class ReservationService {
    private final ScreeningRepository screeningRepository;
    private final ReservationRepository reservationRepository;

    public ReservationService(ScreeningRepository screeningRepository, ReservationRepository reservationRepository) {
        this.screeningRepository = screeningRepository;
        this.reservationRepository = reservationRepository;
    }

    public PostReserveScreeningResponseModel reserveScreening(PostReserveScreeningRequestModel request) {
        var price = request.getSeats().stream()
                .map(seat -> seat.getTicketType().getPrice())
                .reduce(0f, Float::sum);

        var screening = screeningRepository.getById(request.getScreeningId());

        var tickets = request.getSeats().stream()
                .map(seat -> Ticket.builder().ticketType(seat.getTicketType()).build())
                .collect(Collectors.toList());

        var reservedSeats = screening.getScreeningRoom().getRows().stream()
                .flatMap(row -> row.getSeats().stream())
                .map(seat -> ReservedSeat.builder().seat(seat).build())
                .collect(Collectors.toSet());

        var reservation = Reservation.builder()
                .reservedSeats(reservedSeats)
                .tickets(tickets)
                .screening(screening)
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .build();

        reservedSeats.forEach(reservedSeat -> reservedSeat.setReservation(reservation));
        tickets.forEach(ticket -> ticket.setReservation(reservation));

        reservationRepository.save(reservation);

        return new PostReserveScreeningResponseModel(price, screening.getFromDateTime());
    }
}
