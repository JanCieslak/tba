package io.github.jancieslak.tba.dto;

public class SeatReadModel {
    private int row;
    private int col;
    private boolean available;

    public SeatReadModel(int row, int col, boolean available) {
        this.row = row;
        this.col = col;
        this.available = available;
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

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
