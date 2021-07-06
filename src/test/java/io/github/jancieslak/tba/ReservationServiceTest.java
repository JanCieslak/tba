package io.github.jancieslak.tba;

import io.github.jancieslak.tba.dto.PostReserveScreeningRequestModel;
import io.github.jancieslak.tba.dto.SeatReservationModel;
import io.github.jancieslak.tba.model.*;
import io.github.jancieslak.tba.repository.ReservationRepository;
import io.github.jancieslak.tba.repository.ReservedSeatsRepository;
import io.github.jancieslak.tba.repository.ScreeningRepository;
import io.github.jancieslak.tba.repository.ScreeningRoomRepository;
import io.github.jancieslak.tba.services.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ReservationServiceTest {
    private ReservedSeatsRepository mockReservedSeatsRepository;
    private ScreeningRepository mockScreeningRepository;
    private ReservationRepository mockReservationRepository;
    private ScreeningRoomRepository mockScreeningRoomRepository;
    private ReservationService reservationService;
    private LocalDate todayDate = LocalDate.now();
    private Movie batmanMovie;
    private Screening batmanScreening;
    private Seat seat1;
    private Seat seat2;
    private Seat seat3;
    private Seat seat4;
    private ScreeningRoomRow row;
    private ScreeningRoom screeningRoom;
    private Set<SeatReservationModel> badWantToReserve;
    private Set<ReservedSeat> reservedSeats;
    private Set<SeatReservationModel> wantToReserve;

    @BeforeEach
    public void initialize() {
        mockReservedSeatsRepository = mock(ReservedSeatsRepository.class);
        mockScreeningRepository = mock(ScreeningRepository.class);
        mockReservationRepository = mock(ReservationRepository.class);
        mockScreeningRoomRepository = mock(ScreeningRoomRepository.class);
        reservationService = new ReservationService(mockReservedSeatsRepository,
                mockScreeningRepository,
                mockReservationRepository,
                mockScreeningRoomRepository);

        batmanMovie = Movie.builder().title("Batman").build();
        batmanScreening = Screening.builder()
                .id(0L)
                .movie(batmanMovie)
                .fromDateTime(LocalDateTime.of(todayDate, LocalTime.of(19, 0)))
                .toDateTime(LocalDateTime.of(todayDate, LocalTime.of(21, 0)))
                .build();
        seat1 = Seat.builder()
                .id(0L)
                .seatCol(1)
                .build();
        seat2 = Seat.builder()
                .id(1L)
                .seatCol(2)
                .build();
        seat3 = Seat.builder()
                .id(2L)
                .seatCol(3)
                .build();
        seat4 = Seat.builder()
                .id(3L)
                .seatCol(4)
                .build();
        row = ScreeningRoomRow.builder()
                .screeningRoomRow(1)
                .seats(Set.of(seat1, seat2, seat3, seat4))
                .build();
        seat1.setRow(row);
        seat2.setRow(row);
        seat3.setRow(row);
        seat4.setRow(row);
        screeningRoom = ScreeningRoom.builder()
                .rows(Set.of(row))
                .screenings(Set.of(batmanScreening))
                .build();
        badWantToReserve = Set.of(
                new SeatReservationModel(TicketType.ADULT, 1, 2)
        );
        reservedSeats = Set.of(
                ReservedSeat.builder()
                        .id(0L)
                        .seat(seat1)
                        .build(),
                ReservedSeat.builder()
                        .id(1L)
                        .seat(seat4)
                        .build()
        );
        wantToReserve = Set.of(
                new SeatReservationModel(TicketType.ADULT, 1, 2),
                new SeatReservationModel(TicketType.ADULT, 1, 3)
        );
    }

    @Test
    public void hasValidSeatsPlacementTest() {
        when(mockScreeningRoomRepository.findScreeningRoomByScreeningId(anyLong())).thenReturn(Optional.of(screeningRoom));

        // row of [reserved, wantToReserve, empty, reserved]
        assertFalse(reservationService.hasValidSeatsPlacement(badWantToReserve, reservedSeats, batmanScreening));

        // row of [reserved, wantToReserve, wantToReserve, reserved]
        assertTrue(reservationService.hasValidSeatsPlacement(wantToReserve, reservedSeats, batmanScreening));
    }

    @Test
    public void reserveScreeningTest() {
        var notValidFirstName = "john";
        var validFirstName = "John";
        var notValidLastName1 = "smith";
        var notValidLastName2 = "Smith/brown";
        var notValidLastName3 = "smith/Brown";
        var validLastName = "Smith/Brown";

        Set<SeatReservationModel> notValidSeats = Set.of();
        var validSeats = Set.of(
                new SeatReservationModel(TicketType.ADULT, 1, 1)
        );

        var notValidNameRequest1 = new PostReserveScreeningRequestModel(0, notValidFirstName, validLastName, validSeats);
        var notValidNameRequest2 = new PostReserveScreeningRequestModel(0, validFirstName, notValidLastName1, validSeats);
        var notValidNameRequest3 = new PostReserveScreeningRequestModel(0, validFirstName, notValidLastName2, validSeats);
        var notValidNameRequest4 = new PostReserveScreeningRequestModel(0, validFirstName, notValidLastName3, validSeats);
        var notValidSeatsRequest = new PostReserveScreeningRequestModel(0, validFirstName, validLastName, notValidSeats);
        var validNameAndSeatsRequest = new PostReserveScreeningRequestModel(0, validFirstName, validLastName, validSeats);

        assertNull(reservationService.reserveScreening(notValidNameRequest1));
        assertNull(reservationService.reserveScreening(notValidNameRequest2));
        assertNull(reservationService.reserveScreening(notValidNameRequest3));
        assertNull(reservationService.reserveScreening(notValidNameRequest4));
        assertNull(reservationService.reserveScreening(notValidSeatsRequest));
        assertThrows(NullPointerException.class, () -> reservationService.reserveScreening(validNameAndSeatsRequest));

        var nowTime = LocalTime.now();
        var badScreening = Screening.builder()
                .movie(batmanMovie)
                .fromDateTime(LocalDateTime.of(todayDate, nowTime.minusHours(1)))
                .toDateTime(LocalDateTime.of(todayDate, nowTime.plusHours(1)))
                .build();

        when(mockScreeningRepository.getById(anyLong())).thenReturn(badScreening);

        var notValidTimeRequest = new PostReserveScreeningRequestModel(0, validFirstName, validLastName, validSeats);
        assertNull(reservationService.reserveScreening(notValidTimeRequest));

        when(mockScreeningRepository.getById(anyLong())).thenReturn(batmanScreening);
        when(mockScreeningRoomRepository.findScreeningRoomByScreeningId(anyLong())).thenReturn(Optional.of(screeningRoom));

        var validRequest = new PostReserveScreeningRequestModel(0, validFirstName, validLastName, validSeats);
        var result = reservationService.reserveScreening(validRequest);

        assertEquals(TicketType.ADULT.getPrice(), result.getToPay());
        assertEquals(batmanScreening.getFromDateTime(), result.getExpiresAt());
    }
}
