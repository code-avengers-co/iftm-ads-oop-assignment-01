package model;

import model.enums.DeliveryStatus;
import utils.CreationIdUtils;
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

    public Delivery(Order order) {
        this.id = CreationIdUtils.generateDeliveryId();
        this.order = order;
        this.status = DeliveryStatus.Preparing;
        this.carrier = null;
        this.trackingCode = null;
        this.shippingDate = null;
        this.deliveryDate = null;
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

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
