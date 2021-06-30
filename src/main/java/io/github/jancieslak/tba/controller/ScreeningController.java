package io.github.jancieslak.tba.controller;

import io.github.jancieslak.tba.dto.GetScreeningsRequestModel;
import io.github.jancieslak.tba.dto.GetScreeningsResponseModel;
import io.github.jancieslak.tba.repository.ScreeningRepository;
import io.github.jancieslak.tba.services.ScreeningService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

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
}
