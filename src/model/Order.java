package model;

import model.enums.OrderStatus;
import utils.CreationIdUtils;
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
        this.id = CreationIdUtils.generateOrderId();
        this.user = user;
        this.totalValue = totalValue;
        this.paymentMethod = paymentMethod;

        this.status = OrderStatus.Created;
        this.coupon = null;
        this.createdAt = SystemClock.now();
        this.updatedAt = SystemClock.now();
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
