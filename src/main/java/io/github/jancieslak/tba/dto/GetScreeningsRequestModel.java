package io.github.jancieslak.tba.dto;

import java.time.LocalDateTime;

public class GetScreeningsRequestModel {
    private LocalDateTime beginFrom;
    private LocalDateTime beginTo;

    public GetScreeningsRequestModel(LocalDateTime beginFrom, LocalDateTime beginTo) {
        this.beginFrom = beginFrom;
        this.beginTo = beginTo;
    }

    public LocalDateTime getBeginFrom() {
        return beginFrom;
    }

    public void setBeginFrom(LocalDateTime beginFrom) {
        this.beginFrom = beginFrom;
    }

    public LocalDateTime getBeginTo() {
        return beginTo;
    }

    public void setBeginTo(LocalDateTime beginTo) {
        this.beginTo = beginTo;
    }
}
