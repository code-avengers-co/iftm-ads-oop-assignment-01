package model;

import utils.CreationIdUtils;
import utils.SystemClock;

import java.time.LocalDateTime;

public class Product {
    private int id;
    private String name;
    private String description;
    private double price;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int stockQuantity;

    public Product(String name, String description, double price){
        this.name = name;
        this.description = description;
        this.price = price;
        this.active = true;
        this.stockQuantity = 0;
        this.createdAt = SystemClock.now();
    }

    public Product(int id, String name, String description, double price, boolean active, int stockQuantity, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.active = active;
        this.stockQuantity = stockQuantity;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean isActive) {
        this.active = isActive;
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

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    @Override
    public String toString() {
        return "ID: " + this.getId() +
                " | Name: " + this.getName() +
                " | Price: R$" + String.format("%.2f", this.getPrice()) +
                " | Stock: " + this.getStockQuantity() + " units";
    }
}
