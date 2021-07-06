package io.github.jancieslak.tba.controller;

import io.github.jancieslak.tba.dto.*;
import io.github.jancieslak.tba.repository.ScreeningRepository;
import io.github.jancieslak.tba.repository.ScreeningRoomRepository;
import io.github.jancieslak.tba.repository.SeatRepository;
import io.github.jancieslak.tba.services.ScreeningService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class ScreeningController {
    private final ScreeningService screeningService;

    public ScreeningController(ScreeningService screeningService) {
        this.screeningService = screeningService;
    }

    @GetMapping("/screenings")
    ResponseEntity<List<GetScreeningsResponseModel>> getScreeningsInInterval(@RequestBody @Valid GetScreeningsRequestModel request) {
        var result = screeningService.getScreeningsInInterval(request);
        if (result == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/screenings/seats")
    ResponseEntity<GetScreeningSeatsResponseModel> getScreeningSeats(@RequestBody @Valid GetScreeningSeatsRequestModel request) {
        var result = screeningService.getScreeningSeats(request);
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }
}
