package io.github.jancieslak.tba.services;

import io.github.jancieslak.tba.dto.PostReserveScreeningRequestModel;
import io.github.jancieslak.tba.dto.PostReserveScreeningResponseModel;
import io.github.jancieslak.tba.dto.SeatReservationModel;
import io.github.jancieslak.tba.model.Reservation;
import io.github.jancieslak.tba.model.Ticket;
import io.github.jancieslak.tba.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class ReservationService {
    private final static Pattern FIRSTNAME_PATTERN = Pattern.compile("([A-ZĄĆĘŁŃÓŚŹŻ][a-ząćęłńóśźż]{2,})");
    private final static Pattern LASTNAME_PATTERN = Pattern.compile("([A-ZĄĆĘŁŃÓŚŹŻ][a-ząćęłńóśźż]{2,})(/[A-ZĄĆĘŁŃÓŚŹŻ][a-ząćęłńóśźż]{2,})?");

    private final ReservedSeatsRepository reservedSeatsRepository;
    private final ScreeningRepository screeningRepository;
    private final ReservationRepository reservationRepository;
    private final ScreeningRoomRepository screeningRoomRepository;

    public ReservationService(ReservedSeatsRepository reservedSeatsRepository,
                              ScreeningRepository screeningRepository,
                              ReservationRepository reservationRepository,
                              ScreeningRoomRepository screeningRoomRepository) {
        this.reservedSeatsRepository = reservedSeatsRepository;
        this.screeningRepository = screeningRepository;
        this.reservationRepository = reservationRepository;
        this.screeningRoomRepository = screeningRoomRepository;
    }

    public PostReserveScreeningResponseModel reserveScreening(PostReserveScreeningRequestModel request) {
        if (!FIRSTNAME_PATTERN.matcher(request.getFirstname()).matches() ||
                !LASTNAME_PATTERN.matcher(request.getLastname()).matches() ||
                request.getSeats().size() == 0) {
            return null;
        }

        var screening = screeningRepository.getById(request.getScreeningId());

        if (LocalDateTime.now().plusMinutes(15).isAfter(screening.getFromDateTime())) {
            System.out.println("Too late");
            return null;
        }

        var tickets = request.getSeats().stream()
                .map(seat -> Ticket.builder().ticketType(seat.getTicketType()).build())
                .collect(Collectors.toList());

        var reservedSeats = reservedSeatsRepository.findAllByReservationScreeningId(screening.getId());

        // Check for single place between already reserved seats
        // Get request seats columns grouped by row
        var partitionedRequestSeats = request.getSeats().stream()
                .collect(Collectors.groupingBy(SeatReservationModel::getRow));

        // Get reserved seats columns filtered by request seats rows
        var partitionedReservedSeats = reservedSeats.stream()
                .filter(reservedSeat -> partitionedRequestSeats.get(reservedSeat.getSeat().getRow().getScreeningRoomRow()) != null)
                .collect(Collectors.groupingBy(reservedSeat -> reservedSeat.getSeat().getRow().getScreeningRoomRow()));

        var screeningRoomOption = screeningRoomRepository.findScreeningRoomByScreeningId(screening.getId());

        if (screeningRoomOption.isEmpty()) {
            return null;
        }

        var screeningRoom = screeningRoomOption.get();

        // Assemble a grid of blank seats and reserved/requested seats
        // If a place is reserved or in requested seats it's true otherwise it's false
        var screeningRoomRows = screeningRoom.getRows().stream()
                .filter(row -> partitionedRequestSeats.get(row.getScreeningRoomRow()) != null)
                .map(row -> {
                    var requestSeatsSet = partitionedRequestSeats.get(row.getScreeningRoomRow()).stream()
                            .map(SeatReservationModel::getCol)
                            .collect(Collectors.toSet());
                    var reservedSeatsSetOption = partitionedReservedSeats.get(row.getScreeningRoomRow());
                    Set<Integer> reservedSeatsSet = null;

                    if (reservedSeatsSetOption != null) {
                        reservedSeatsSet = reservedSeatsSetOption.stream()
                                .map(reservedSeat -> reservedSeat.getSeat().getSeatCol())
                                .collect(Collectors.toSet());
                    }

                    List<Boolean> result = new ArrayList<>();

                    for (int i = 0; i < row.getSeats().size(); i++) {
                        int col = i + 1;

                        if (requestSeatsSet.contains(col) || (reservedSeatsSet != null && reservedSeatsSet.contains(col))) {
                            result.add(true);
                        } else {
                            result.add(false);
                        }
                    }

                    return result;
                })
                .collect(Collectors.toSet());

        for (var row : screeningRoomRows) {
            for (int col = 1; col < row.size() - 1; col++) {
                var prevValue = row.get(col - 1);
                var value = row.get(col);
                var nextValue = row.get(col + 1);

                // There is a single place left between two reserved/requested seats
                if (!value && prevValue && nextValue) {
                    return null;
                }
            }
        }

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

        var price = request.getSeats().stream()
                .map(seat -> seat.getTicketType().getPrice())
                .reduce(0f, Float::sum);

        return new PostReserveScreeningResponseModel(price, screening.getFromDateTime());
    }
}
