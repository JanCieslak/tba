package io.github.jancieslak.tba.dto;

import java.util.Set;

public class GetScreeningSeatsResponseModel {
    private long screeningId;
    private Set<SeatReadModel> seats;

    public GetScreeningSeatsResponseModel(long screeningId, Set<SeatReadModel> seats) {
        this.screeningId = screeningId;
        this.seats = seats;
    }

    public long getScreeningId() {
        return screeningId;
    }

    public void setScreeningId(long screeningId) {
        this.screeningId = screeningId;
    }

    public Set<SeatReadModel> getSeats() {
        return seats;
    }

    public void setSeats(Set<SeatReadModel> seats) {
        this.seats = seats;
    }
}
