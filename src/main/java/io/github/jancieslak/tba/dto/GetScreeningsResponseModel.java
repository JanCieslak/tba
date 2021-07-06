package io.github.jancieslak.tba.dto;

import java.time.LocalDateTime;

public class GetScreeningsResponseModel {
    private String title;
    private LocalDateTime from;
    private LocalDateTime to;

    public GetScreeningsResponseModel(String title, LocalDateTime from, LocalDateTime to) {
        this.title = title;
        this.from = from;
        this.to = to;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public void setFrom(LocalDateTime from) {
        this.from = from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    public void setTo(LocalDateTime to) {
        this.to = to;
    }
}
