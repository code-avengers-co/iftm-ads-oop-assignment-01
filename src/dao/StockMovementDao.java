package dao;

import model.StockMovement;

import java.time.LocalDateTime;

public class StockMovementDao {
    private StockMovement[] stockMovementDb;
    private int stockMovementCount;

    public  StockMovementDao(int length){
        stockMovementDb = new StockMovement[length];
        stockMovementCount = 0;
    }

    public boolean saveStockMovement(StockMovement stockMovement) {
        if (stockMovementCount >= stockMovementDb.length){
            return false; // No more space to save new stock movement
        }

        stockMovementDb[stockMovementCount] = stockMovement;
        stockMovementCount++;

        return true;
    }

    public StockMovement findById(int id){
        for (int i = 0; i < stockMovementCount; i++) {
            if (stockMovementDb[i].getId() == id){
                return stockMovementDb[i];
            }
        }

        return null; // Stock movement not found
    }

    public StockMovement[] getAllStockMovement(){
        StockMovement[] stockMovements = new StockMovement[stockMovementCount];
        int currentIndex = 0;

        for (int i = 0; i < stockMovementCount; i++) {
            if (stockMovementDb[i] != null){
                stockMovements[currentIndex] = stockMovementDb[i];
                currentIndex++;
            }
        }

        return stockMovements;
    }

    public boolean updateStockMovement(StockMovement updatedStockMovement) {
        StockMovement stockMovement = findById(updatedStockMovement.getId());
        if (stockMovement == null) {
            return false; // Stock movement not found
        }

        stockMovement.setQuantity(updatedStockMovement.getQuantity());
        stockMovement.setType(updatedStockMovement.getType());
        stockMovement.setUnitValue(updatedStockMovement.getUnitValue());
        stockMovement.setUpdatedAt(LocalDateTime.now());

        return true;
    }
}
