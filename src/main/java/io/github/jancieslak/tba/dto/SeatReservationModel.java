package io.github.jancieslak.tba.dto;

import io.github.jancieslak.tba.model.TicketType;

import java.util.Objects;

public class SeatReservationModel {
    private TicketType ticketType;
    private int row;
    private int col;

    public SeatReservationModel(TicketType ticketType, int row, int col) {
        this.ticketType = ticketType;
        this.row = row;
        this.col = col;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SeatReservationModel that = (SeatReservationModel) o;
        return row == that.row && col == that.col && ticketType == that.ticketType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticketType, row, col);
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }
}
