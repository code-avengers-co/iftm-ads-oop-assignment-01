package model;

import javax.swing.text.html.HTMLDocument;
import java.time.LocalDateTime;

public class Coupon {
    private int id;
    private String code;
    private String discountType;
    private double discountValue;
    private double minimumPrice;
    private boolean active;
    private LocalDateTime expiresAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Coupon(int id, String code, String discounType, double discountValue, double minimumPrice, LocalDateTime expiresAt) {
        this.id = id;
        this.code = code;
        this.discountType = discounType;
        this.discountValue = discountValue;
        this.minimumPrice = minimumPrice;
        this.expiresAt = expiresAt;
        this.active = true;
        this.createdAt = LocalDateTime.now();
    }

    public int getId() { return id; }

    public String getCode() { return code; }

    public void setCode(String code) { this.code = code; }

    public String getDiscountType() { return discountType; }

    public void setDiscountType(String discountType) { this.discountType = discountType; }

    public double getDiscountValue() { return discountValue; }

    public void setDiscountValue(double discountValue) { this.discountValue = discountValue; }

    public double getMinimumPrice() { return minimumPrice; }

    public void setMinimumPrice(double minimumPrice) { this.minimumPrice = minimumPrice; }

    public boolean isActive() { return active; }

    public void setActive(boolean active) { this.active = active; }

    public LocalDateTime getExpiresAt() { return expiresAt; }

    public void getExpiresAt(LocalDateTime expiresAt) { this.expiresAt = expiresAt; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public LocalDateTime getUpdatedAt() { return createdAt; }

    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}