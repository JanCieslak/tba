package io.github.jancieslak.tba.services;

import io.github.jancieslak.tba.dto.GetScreeningsRequestModel;
import io.github.jancieslak.tba.dto.GetScreeningsResponseModel;
import io.github.jancieslak.tba.repository.ScreeningRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScreeningService {
    private final ScreeningRepository screeningRepository;

    public ScreeningService(ScreeningRepository screeningRepository) {
        this.screeningRepository = screeningRepository;
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

    private boolean isSameDate(LocalDateTime date1, LocalDateTime date2) {
        return date1.format(DateTimeFormatter.ofPattern("yyyyMMdd")).equals(date2.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
    }
}
