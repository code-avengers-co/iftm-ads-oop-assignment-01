package view;

import controller.ProductController;
import model.Product;
import utils.ProductUtils;

import java.util.Scanner;

public class ProductView {
    private ProductController productController;
    private Scanner scanner;

    public ProductView(ProductController controller) {
        this.productController = controller;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        int option;
        do {
            option = getOption();
            switch (option) {
                case 1:
                    renderRegisterProduct();
                    break;
                case 2:
                    renderListProducts();
                    break;
                case 3:
                    renderUpdateProduct();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
            }
        } while (option != 0);
    }

    private int getOption() {
        String menu = """
                --- PRODUCT MENU ---
                1 - Register Product
                2 - List Products
                3 - Edit Product
                0 - Back
                """;
        System.out.print(menu);

        int option = -1;
        do {
            System.out.print("Choose an option: ");
            option = Integer.parseInt(scanner.nextLine());
        } while (option < 0 || option > 3);

        return option;
    }

    private void renderRegisterProduct() {
        System.out.println("Enter product details:");

        int generatedId = ProductUtils.getNextId();
        System.out.println("Generated ID: " + generatedId);

        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Price: ");
        double price = Double.parseDouble(scanner.nextLine());

        boolean success = productController.registerProduct(generatedId, name, price);

        if (success) {
            System.out.println("Product saved successfully!");
        } else {
            System.out.println("Error: Database is full.");
        }
    }

    private void renderListProducts(){
        System.out.println("Listing all active products...");

        Product[] activeProducts = productController.getActiveProducts();
        if (activeProducts.length == 0){
            System.out.println("No active products found.");
            return;
        }

        for (Product product : activeProducts) {
            if (product != null) {
                System.out.println(product);
            }
        }
    }

    private void renderUpdateProduct() {
        StringBuilder message = new StringBuilder("--- EDIT PRODUCT ---");
        message.append("\nAvailable products:");

        Product[] allProducts = productController.getAllProducts();
        for (Product product : allProducts) {
            if (product != null) {
                message.append("\n").append(product);
            }
        }

        System.out.println(message);

        System.out.print("Enter the ID of the product to edit: ");
        int id = Integer.parseInt(scanner.nextLine());

        boolean hasProduct = productController.hasProduct(id);
        if (!hasProduct) {
            System.out.println("Error: Product not found.");
            return;
        }

        System.out.print("New Name: ");
        String name = scanner.nextLine();

        System.out.print("New Price: ");
        double price = Double.parseDouble(scanner.nextLine());

        boolean isUpdated = productController.updateProduct(id, name, price);

        if (isUpdated){
            System.out.println("Product updated successfully!");
        } else {
            System.out.println("Error: Product not found.");
        }
    }
}