package dao;

import model.StockMovement;

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
}
