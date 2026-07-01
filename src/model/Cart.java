package model;

import model.enums.CartStatus;

import utils.SystemClock;

import java.time.LocalDateTime;

public class Cart {
    private int id;
    private User user;
    private CartStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Cart(User user) {
        this.user = user;
        this.status = CartStatus.Open;
        this.createdAt = SystemClock.now();
        this.updatedAt = SystemClock.now();
    }

    public Cart(int id, User user, CartStatus status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.user = user;
        this.status = status;
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

    public CartStatus getStatus() {
        return status;
    }

    public void setStatus(CartStatus status) {
        this.status = status;
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
