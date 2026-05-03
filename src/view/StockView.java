package view;

import controller.ProductController;
import controller.StockController;
import model.enums.MovementType;
import utils.InputUtils;

public class StockView {
    private StockController stockController;
    private ProductView productView;

    public StockView(StockController stockController, ProductView productView) {
        this.stockController = stockController;
        this.productView = productView;
    }

    public void handleManualStockEntry() {
        System.out.println("\n--- MANUAL STOCK ENTRY ---");

        productView.showAvailableProducts();

        int productId = InputUtils.readInt("Enter Product ID (-1 to cancel): ", -1, Integer.MAX_VALUE);
        if (productId == -1){
            return;
        }

        int quantity = InputUtils.readInt("Enter Quantity: ", 1, Integer.MAX_VALUE);

        System.out.println("Select Movement Type:");
        System.out.println("1 - IN (Entrada)");
        System.out.println("2 - OUT (Saída/Baixa)");
        System.out.println("3 - ADJUST (Ajuste Absoluto)");
        int typeOption = InputUtils.readInt("Choose an option: ", 1, 3);

        MovementType type;
        switch (typeOption){
            case 1:
                type = MovementType.IN;
                break;
            case 2:
                type = MovementType.OUT;
                break;
            default:
                type = MovementType.ADJUST;
                break;
        }

        double unitValue = InputUtils.readInt("Enter the Unit Value (Cost/Adjustment): ", 0, Integer.MAX_VALUE);

        boolean success = stockController.registerStockMovement(productId, quantity, type, unitValue);

        if (success) {
            System.out.println("Stock updated successfully!");
        } else {
            System.out.println("Error: Could not update stock. Check if Product ID is valid.");
        }
    }
}
