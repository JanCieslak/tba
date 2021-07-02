package io.github.jancieslak.tba.dto;

import java.time.LocalDateTime;

public class PostReserveScreeningResponseModel {
    private float toPay;
    private LocalDateTime expiresAt;

    public PostReserveScreeningResponseModel(float toPay, LocalDateTime expiresAt) {
        this.toPay = toPay;
        this.expiresAt = expiresAt;
    }

    public float getToPay() {
        return toPay;
    }

    public void setToPay(float toPay) {
        this.toPay = toPay;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }
}
