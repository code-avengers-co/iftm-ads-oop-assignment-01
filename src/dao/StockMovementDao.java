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

    public StockMovement[] findMovementsByProductId(int productId) {
        StockMovement[] tempMovements = new StockMovement[stockMovementCount];
        int count = 0;

        for (int i = 0; i < stockMovementCount; i++) {
            if (stockMovementDb[i].getProduct().getId() == productId) {
                tempMovements[count] = stockMovementDb[i];
                count++;
            }
        }

        StockMovement[] exactMovements = new StockMovement[count];
        for (int i = 0; i < count; i++) {
            exactMovements[i] = tempMovements[i];
        }

        return exactMovements;
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

    public boolean deleteStockMovement(int id) {
        int indexToDeleted = -1;
        for (int i = 0; i < stockMovementCount; i++) {
            if (this.stockMovementDb[i].getId() == id){
                indexToDeleted = i;
                break;
            }
        }

        if (indexToDeleted == -1){
            return false; // Stock not found!
        }

        stockMovementDb[indexToDeleted] = stockMovementDb[stockMovementCount - 1];
        stockMovementDb[stockMovementCount - 1] = null;
        stockMovementCount--;

        return true;
    }
}
