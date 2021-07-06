package io.github.jancieslak.tba.dto;

import java.time.LocalDateTime;

public class GetScreeningSeatsRequestModel {
    private LocalDateTime from;
    private String title;

    public GetScreeningSeatsRequestModel(LocalDateTime from, String title) {
        this.from = from;
        this.title = title;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public void setFrom(LocalDateTime from) {
        this.from = from;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
