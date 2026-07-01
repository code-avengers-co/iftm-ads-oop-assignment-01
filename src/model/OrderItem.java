package model;


import utils.SystemClock;

import java.time.LocalDateTime;

public class OrderItem {
     private int id;
     private Order order;
     private Product product;
     private int quantity;
     private double unitPrice;
     private double subTotal;
     private LocalDateTime createdAt;
     private LocalDateTime updatedAt;

    public OrderItem(Order order, Product product, int quantity, double unitPrice) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;

        this.subTotal = quantity * unitPrice;
        this.createdAt = SystemClock.now();
        this.updatedAt = SystemClock.now();
    }

    public OrderItem(int id, Order order, Product product, int quantity, double unitPrice, double subTotal, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.subTotal = subTotal;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void setId(int id) {
        this.id = id;
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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.subTotal = this.quantity * this.unitPrice;
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
