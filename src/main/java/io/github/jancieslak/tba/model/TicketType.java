package io.github.jancieslak.tba.model;

public enum TicketType {
    ADULT(25f), STUDENT(18f), CHILD(12.5f);

    private float price;

    TicketType(float price) {
        this.price = price;
    }

    public float getPrice() {
        return price;
    }
}
