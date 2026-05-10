package controller;

import dao.ProductDao;
import dao.StockMovementDao;
import model.Product;
import model.StockMovement;
import model.enums.MovementType;

public class StockController {
    private StockMovementDao stockMovementDao;
    private ProductDao productDao;

    public StockController(StockMovementDao stockMovementDao, ProductDao productDao) {
        this.stockMovementDao = stockMovementDao;
        this.productDao = productDao;
    }

    public boolean registerStockMovement(int productId, int quantity, MovementType type, double unitValue) {
        Product product = productDao.findById(productId);
        if (product == null) {
            return false; // Product not found
        }

        // 1. Register a new StockMovement
        StockMovement movement = new StockMovement(
                product,
                quantity,
                type,
                unitValue
        );

        boolean saved = stockMovementDao.saveStockMovement(movement);
        if (!saved) {
            return false;
        }

        // 3. Update the product stock quantity based on the movement type
        int currentStock = product.getStockQuantity();
        switch (type) {
            case IN:
                product.setStockQuantity(currentStock + quantity);
                break;
            case OUT:
                product.setStockQuantity(currentStock - quantity);
                break;
            case ADJUST:
                product.setStockQuantity(quantity);
                break;
        }

        return productDao.updateProduct(product);
    }
}
