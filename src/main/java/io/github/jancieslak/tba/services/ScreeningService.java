package io.github.jancieslak.tba.services;

import io.github.jancieslak.tba.dto.*;
import io.github.jancieslak.tba.repository.ScreeningRepository;
import io.github.jancieslak.tba.repository.ScreeningRoomRepository;
import io.github.jancieslak.tba.repository.SeatRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ScreeningService {
    private final ScreeningRepository screeningRepository;
    private final ScreeningRoomRepository screeningRoomRepository;
    private final SeatRepository seatRepository;

    public ScreeningService(ScreeningRepository screeningRepository, ScreeningRoomRepository screeningRoomRepository, SeatRepository seatRepository) {
        this.screeningRepository = screeningRepository;
        this.screeningRoomRepository = screeningRoomRepository;
        this.seatRepository = seatRepository;
    }

    public List<GetScreeningsResponseModel> getScreeningsInInterval(GetScreeningsRequestModel request) {
        if (!isSameDate(request.getBeginFrom(), request.getBeginTo())) {
            return null;
        }

        var response = new ArrayList<GetScreeningsResponseModel>();
        var result = screeningRepository.findByDateTimeInterval(request.getBeginFrom(), request.getBeginTo());

        for (var screening : result) {
            response.add(new GetScreeningsResponseModel(screening.getMovie().getTitle(), screening.getFromDateTime(), screening.getToDateTime()));
        }

        return response;
    }

    public GetScreeningSeatsResponseModel getScreeningSeats(GetScreeningSeatsRequestModel request) {
        var screeningOption = screeningRepository.findByFromDateTimeAndMovieTitle(request.getFrom(), request.getTitle());

        if (screeningOption.isEmpty()) {
            return null;
        }

        var screening = screeningOption.get();
        var screeningRoomOption = screeningRoomRepository.findScreeningRoomByScreeningId(screening.getId());

        if (screeningRoomOption.isEmpty()) {
            return null;
        }

        var screeningRoom = screeningRoomOption.get();
        var reservedSeats = seatRepository.findReservedSeatsByScreeningId(screening.getId());

        var seats = screeningRoom.getRows().stream()
                .flatMap(row -> row.getSeats().stream())
                .map(seat -> {
                    if (reservedSeats.contains(seat.getId())) {
                        return new SeatReadModel(seat.getRow().getScreeningRoomRow(), seat.getSeatCol(), false);
                    }
                    return new SeatReadModel(seat.getRow().getScreeningRoomRow(), seat.getSeatCol(), true);
                })
                .sorted(Comparator.comparingInt(SeatReadModel::getRow).thenComparingInt(SeatReadModel::getCol))
                .collect(Collectors.toCollection(LinkedHashSet::new));

        return new GetScreeningSeatsResponseModel(screening.getId(), seats);
    }

    private boolean isSameDate(LocalDateTime date1, LocalDateTime date2) {
        return date1.format(DateTimeFormatter.ofPattern("yyyyMMdd")).equals(date2.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
    }
}
