package model;

import model.enums.DiscountType;
import utils.SystemClock;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Coupon {
    private int id;
    private String code;
    private DiscountType type;
    private double discountValue;
    private double minimumPrice;
    private boolean active;
    private LocalDate expiresAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Coupon(int id, String code, DiscountType discounType, double discountValue, double minimumPrice, LocalDate expiresAt) {
        this.id = id;
        this.code = code;
        this.type = discounType;
        this.discountValue = discountValue;
        this.minimumPrice = minimumPrice;
        this.expiresAt = expiresAt;

        this.active = true;
        this.createdAt = SystemClock.now();
        this.updatedAt = SystemClock.now();
    }


    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DiscountType getType() {
        return type;
    }

    public void setType(DiscountType type) {
        this.type = type;
    }

    public double getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(double discountValue) {
        this.discountValue = discountValue;
    }

    public double getMinimumPrice() {
        return minimumPrice;
    }

    public void setMinimumPrice(double minimumPrice) {
        this.minimumPrice = minimumPrice;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDate getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDate expiresAt) {
        this.expiresAt = expiresAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        String symbol = (this.type == DiscountType.Fixed) ? "R$" : "%";
        return "Code: " + this.code +
                " | Discount: " + this.discountValue + symbol +
                " | Min. Price: R$" + String.format("%.2f", this.minimumPrice) +
                " | Expires: " + this.expiresAt;
    }
}