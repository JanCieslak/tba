package io.github.jancieslak.tba;

import io.github.jancieslak.tba.dto.GetScreeningSeatsRequestModel;
import io.github.jancieslak.tba.dto.GetScreeningsRequestModel;
import io.github.jancieslak.tba.dto.GetScreeningsResponseModel;
import io.github.jancieslak.tba.dto.SeatReadModel;
import io.github.jancieslak.tba.model.*;
import io.github.jancieslak.tba.repository.ScreeningRepository;
import io.github.jancieslak.tba.repository.ScreeningRoomRepository;
import io.github.jancieslak.tba.repository.SeatRepository;
import io.github.jancieslak.tba.services.ScreeningService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ScreeningServiceTest {
    private ScreeningRepository mockScreeningRepository;
    private ScreeningRoomRepository mockScreeningRoomRepository;
    private SeatRepository mockSeatRepository;
    private ScreeningService screeningService;

    @BeforeEach
    public void initialize() {
        mockScreeningRepository = mock(ScreeningRepository.class);
        mockScreeningRoomRepository = mock(ScreeningRoomRepository.class);
        mockSeatRepository = mock(SeatRepository.class);
        screeningService = new ScreeningService(mockScreeningRepository, mockScreeningRoomRepository, mockSeatRepository);
    }

    @Test
    public void getScreenInIntervalTest() {
        // Wrong time
        var todayDate = LocalDate.now();
        var badBeforeTime = LocalTime.of(10, 0);
        var badAfterTime = LocalTime.of(9, 0);
        var badInput = new GetScreeningsRequestModel(LocalDateTime.of(todayDate, badBeforeTime), LocalDateTime.of(todayDate, badAfterTime));
        assertNull(screeningService.getScreeningsInInterval(badInput));

        // Wrong date
        var anotherDate = LocalDate.MIN;
        var beforeTime = LocalTime.of(8, 0);
        var afterTime = LocalTime.of(19, 0);
        var badInput2 = new GetScreeningsRequestModel(LocalDateTime.of(todayDate, beforeTime), LocalDateTime.of(anotherDate, afterTime));
        assertNull(screeningService.getScreeningsInInterval(badInput2));

        var batmanMovie = Movie.builder().title("Batman").build();
        var jawsScreening = Screening.builder()
                .movie(Movie.builder().title("Jaws").build())
                .fromDateTime(LocalDateTime.of(todayDate, LocalTime.of(15, 0)))
                .toDateTime(LocalDateTime.of(todayDate, LocalTime.of(17, 0)))
                .build();

        var batmanScreening1 = Screening.builder()
                .movie(batmanMovie)
                .fromDateTime(LocalDateTime.of(todayDate, LocalTime.of(19, 0)))
                .toDateTime(LocalDateTime.of(todayDate, LocalTime.of(21, 0)))
                .build();

        var batmanScreening2 = Screening.builder()
                .movie(batmanMovie)
                .fromDateTime(LocalDateTime.of(todayDate, LocalTime.of(8, 0)))
                .toDateTime(LocalDateTime.of(todayDate, LocalTime.of(12, 0)))
                .build();

        when(mockScreeningRepository.findByDateTimeInterval(any(), any()))
                .thenReturn(List.of(
                        jawsScreening,
                        batmanScreening1,
                        batmanScreening2
                ));

        var input = new GetScreeningsRequestModel(LocalDateTime.of(todayDate, beforeTime), LocalDateTime.of(todayDate, afterTime));
        var result = screeningService.getScreeningsInInterval(input);

        assertEquals(3, result.size());

        // #1 Item
        assertEquals("Batman", result.get(0).getTitle());
        assertEquals(LocalDateTime.of(todayDate, LocalTime.of(8, 0)), result.get(0).getFrom());
        // #2 Item
        assertEquals("Batman", result.get(1).getTitle());
        assertEquals(LocalDateTime.of(todayDate, LocalTime.of(19, 0)), result.get(1).getFrom());
        // #3 Item
        assertEquals("Jaws", result.get(2).getTitle());
        assertEquals(LocalDateTime.of(todayDate, LocalTime.of(15, 0)), result.get(2).getFrom());
    }

    @Test
    public void getScreeningSeatsTest() {
        when(mockScreeningRepository.findByFromDateTimeAndMovieTitle(any(), any())).thenReturn(Optional.empty());
        var todayDate = LocalDate.now();
        var time = LocalTime.of(10, 0);
        var request = new GetScreeningSeatsRequestModel(LocalDateTime.of(todayDate, time), "Batman");
        assertNull(screeningService.getScreeningSeats(request));

        var batmanMovie = Movie.builder().title("Batman").build();
        var batmanScreening = Screening.builder()
                .id(0L)
                .movie(batmanMovie)
                .fromDateTime(LocalDateTime.of(todayDate, LocalTime.of(19, 0)))
                .toDateTime(LocalDateTime.of(todayDate, LocalTime.of(21, 0)))
                .build();

        when(mockScreeningRepository.findByFromDateTimeAndMovieTitle(any(), any())).thenReturn(Optional.of(batmanScreening));
        when(mockScreeningRoomRepository.findScreeningRoomByScreeningId(anyLong())).thenReturn(Optional.empty());
        assertNull(screeningService.getScreeningSeats(request));

        var seat1 = Seat.builder()
                .id(0L)
                .seatCol(1)
                .build();

        var seat2 = Seat.builder()
                .id(1L)
                .seatCol(2)
                .build();

        var seat3 = Seat.builder()
                .id(2L)
                .seatCol(3)
                .build();

        var seat4 = Seat.builder()
                .id(3L)
                .seatCol(4)
                .build();

        var row = ScreeningRoomRow.builder()
                .screeningRoomRow(0)
                .seats(Set.of(seat1, seat2, seat3, seat4))
                .build();

        seat1.setRow(row);
        seat2.setRow(row);
        seat3.setRow(row);
        seat4.setRow(row);

        var screeningRoom = ScreeningRoom.builder()
                .rows(Set.of(row))
                .screenings(Set.of(batmanScreening))
                .build();

        when(mockScreeningRoomRepository.findScreeningRoomByScreeningId(anyLong())).thenReturn(Optional.of(screeningRoom));
        when(mockSeatRepository.findIdsByReservedSeatsAndScreeningId(anyLong())).thenReturn(List.of(0L, 1L));

        var result = screeningService.getScreeningSeats(request);
        assertEquals(4, result.getSeats().size());

        var availableSeats = result.getSeats().stream()
                .filter(SeatReadModel::isAvailable)
                .collect(Collectors.toSet());
        assertEquals(2, availableSeats.size());
    }
}
