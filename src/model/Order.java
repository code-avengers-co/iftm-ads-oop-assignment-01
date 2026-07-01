package model;

import model.enums.OrderStatus;

import utils.SystemClock;

import java.time.LocalDateTime;

public class Order {
    private int id;
    private User user;
    private Coupon coupon;
    private OrderStatus status;
    private double totalValue;
    private String paymentMethod;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Order(User user, double totalValue, String paymentMethod) {
        this.user = user;
        this.totalValue = totalValue;
        this.paymentMethod = paymentMethod;

        this.status = OrderStatus.Created;
        this.coupon = null;
        this.createdAt = SystemClock.now();
        this.updatedAt = SystemClock.now();
    }

    public Order(int id, User user, Coupon coupon, OrderStatus status, double totalValue, String paymentMethod, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.user = user;
        this.coupon = coupon;
        this.status = status;
        this.totalValue = totalValue;
        this.paymentMethod = paymentMethod;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(double totalValue) {
        this.totalValue = totalValue;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod){
        this.paymentMethod = paymentMethod;
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
