package model;

import model.enums.MovementType;
import utils.CreationIdUtils;
import utils.SystemClock;

import java.time.LocalDateTime;

public class StockMovement {
    private int id;
    private Product product;
    private int quantity;
    private MovementType type;
    private double unitValue;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public StockMovement(Product product, int quantity, MovementType type, double unitValue) {
        this.id = CreationIdUtils.generateStockMovementId();
        this.product = product;
        this.quantity = quantity;
        this.type = type;
        this.unitValue = unitValue;
        this.createdAt = SystemClock.now();
        this.updatedAt = SystemClock.now();
    }

    public int getId() {
        return id;
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
    }

    public MovementType getType() {
        return type;
    }

    public void setType(MovementType type) {
        this.type = type;
    }

    public double getUnitValue() {
        return unitValue;
    }

    public void setUnitValue(double unitValue) {
        this.unitValue = unitValue;
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
