package io.github.jancieslak.tba.controller;

import io.github.jancieslak.tba.dto.PostReserveScreeningRequestModel;
import io.github.jancieslak.tba.dto.PostReserveScreeningResponseModel;
import io.github.jancieslak.tba.services.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/reservation")
    ResponseEntity<PostReserveScreeningResponseModel> reserveScreening(@RequestBody @Valid PostReserveScreeningRequestModel request) {
        return ResponseEntity.ok(reservationService.reserveScreening(request));
    }
}
