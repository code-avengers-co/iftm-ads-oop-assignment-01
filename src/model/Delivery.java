package model;

import model.enums.DeliveryStatus;
import utils.SystemClock;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Delivery {
    private int id;
    private Order order;
    private DeliveryStatus status;
    private String carrier;
    private String trackingCode;
    private LocalDate shippingDate;
    private LocalDate deliveryDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Delivery(int id, Order order, String carrier, String trackingCode, LocalDate shippingDate) {
        this.id = id;
        this.order = order;
        this.carrier = carrier;
        this.trackingCode = trackingCode;
        this.shippingDate = shippingDate;
        this.status = DeliveryStatus.Preparing;
        this.createdAt = SystemClock.now();
        this.updatedAt = SystemClock.now();
    }

    public int getId() {
        return id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public DeliveryStatus getStatus() {
        return status;
    }

    public void setStatus(DeliveryStatus status) {
        this.status = status;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public String getTrackingCode() {
        return trackingCode;
    }

    public void setTrackingCode(String trackingCode) {
        this.trackingCode = trackingCode;
    }

    public LocalDate getShippingDate() {
        return shippingDate;
    }

    public void setShippingDate(LocalDate shippingDate) {
        this.shippingDate = shippingDate;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
