package io.github.jancieslak.tba.dto;

import java.util.Set;

public class PostReserveScreeningRequestModel {
    private long screeningId;
    private String firstname;
    private String lastname;
    private Set<SeatReservationModel> seats;

    public PostReserveScreeningRequestModel(long screeningId, String firstname, String lastname, Set<SeatReservationModel> seats) {
        this.screeningId = screeningId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.seats = seats;
    }

    public long getScreeningId() {
        return screeningId;
    }

    public void setScreeningId(long screeningId) {
        this.screeningId = screeningId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Set<SeatReservationModel> getSeats() {
        return seats;
    }

    public void setSeats(Set<SeatReservationModel> seats) {
        this.seats = seats;
    }
}
