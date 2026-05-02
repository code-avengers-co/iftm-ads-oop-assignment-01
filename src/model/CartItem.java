package model;

import java.time.LocalDateTime;

public class CartItem {
    private int id;
    private Cart cart;
    private int quantity;
    private double unitPrice;
    private double subTotal;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CartItem(int id, Cart cart, int quantity, double unitPrice) {
        this.id = id;
        this.cart = cart;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.subTotal = quantity * unitPrice;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.subTotal = quantity * this.unitPrice;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
        this.subTotal = this.quantity * this.unitPrice;
    }

    public double getSubTotal() {
        return subTotal;
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
