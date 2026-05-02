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
}
