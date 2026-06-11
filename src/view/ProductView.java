package view;

import controller.ProductController;
import model.Product;
import utils.InputUtils;

import java.util.List;
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
                case 4:
                    renderDeleteProduct();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
            }
        } while (option != 0);
    }

    public void showAvailableProducts() {
        System.out.println("------------------ PRODUCTS ON SALE ---------------------------");

        List<Product> products = productController.getActiveProducts();
        for (Product product : products) {
            if (product != null) {
                System.out.println(product);
            }
        }

        System.out.println("---------------------------------------------------------------");
    }

    private int getOption() {
        String menu = """
                --- PRODUCT MENU ---
                1 - Register Product
                2 - List Products
                3 - Edit Product
                4 - Delete Product
                0 - Back
                """;
        System.out.println(menu);

        return InputUtils.readInt("Choose an option: ", 0, 4);
    }

    private void renderRegisterProduct() {
        System.out.println("Enter product details:");

        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Price: ");
        double price = Double.parseDouble(scanner.nextLine());

        System.out.print("Description: ");
        String description = scanner.nextLine();

        boolean success = productController.registerProduct(name, description, price);

        if (success) {
            System.out.println("Product saved successfully!");
        } else {
            System.out.println("Error: Database is full.");
        }
    }

    private void renderListProducts() {
        System.out.println("Listing all active products...");
        List<Product> activeProducts = productController.getActiveProducts();

        if (activeProducts.isEmpty()) {
            System.out.println("No active products found.");
            return;
        }

        showProductList("", activeProducts);
    }

    private void renderUpdateProduct() {
        List<Product> activeProducts = productController.getActiveProducts();
        if (activeProducts.isEmpty()){
            System.out.println("--- EDIT PRODUCT ---");
            System.out.println("No products available to edit.");
            return;
        }

        showProductList("--- EDIT PRODUCT ---\nPRODUCT LIST:", activeProducts);

        System.out.print("Enter the ID of the product to edit: ");
        int id = Integer.parseInt(scanner.nextLine());

        boolean isEditable = false;
        for (Product p : activeProducts) {
            if (p.getId() == id) {
                isEditable = true;
                break;
            }
        }

        if (!isEditable) {
            System.out.println("Error: Product not found or is already deleted.");
            return;
        }

        boolean hasProduct = productController.hasProduct(id);
        if (!hasProduct) {
            System.out.println("Error: Product not found.");
            return;
        }

        System.out.print("New Name: ");
        String name = scanner.nextLine();

        System.out.print("New Price: ");
        double price = Double.parseDouble(scanner.nextLine());

        System.out.print("New Description: ");
        String description = scanner.nextLine();

        boolean isUpdated = productController.updateProduct(id, name, description, price);

        if (isUpdated) {
            System.out.println("Product updated successfully!");
        } else {
            System.out.println("Error: Product not found.");
        }
    }

    private void renderDeleteProduct() {
        List<Product> activeProducts = productController.getActiveProducts();

        if (activeProducts.isEmpty()) {
            System.out.println("--- DELETE PRODUCT ---");
            System.out.println("No active products available to delete.");
            return;
        }

        showProductList("--- DELETE PRODUCT ---\nAvailable products:", activeProducts);
        System.out.print("Enter the ID of the product to delete: ");
        int id = Integer.parseInt(scanner.nextLine());

        System.out.println("Are you sure you want to delete this product? (Y/N)");
        String confirmation = scanner.nextLine();

        if (confirmation.equalsIgnoreCase("Y")) {
            boolean isDeleted = productController.deleteProduct(id);
            if (isDeleted) {
                System.out.println("Product deleted successfully!");
            } else {
                System.out.println("Error: Product not found.");
            }
        } else {
            System.out.println("Deletion cancelled.");
        }
    }

    void showProductList(String title, List<Product> products) {
        if (title != null && !title.isEmpty()) {
            System.out.println(title);
        }

        if (products.isEmpty()){
            System.out.println("No products found.");
            return;
        }

        for (Product product : products) {
            if (product != null) {
                System.out.println(product);
            }
        }
    }
}