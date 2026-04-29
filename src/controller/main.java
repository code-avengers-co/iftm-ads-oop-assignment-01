package controller;

import dao.ProductDao;
import model.Product;

public class main {
    static void main(String[] args) {
        // 1. Initialize DAO with space for 5 products
        ProductDao dao = new ProductDao(5);

        // 2. Create mock products
        Product p1 = new Product(1, "Mechanical Keyboard", 150.00);
        p1.setActive(true);

        Product p2 = new Product(2, "Wireless Mouse", 80.00);
        p2.setActive(false); // Inactive product!

        Product p3 = new Product(3, "HD Monitor", 300.00);
        p3.setActive(true);

        // 3. Test saving
        dao.saveProduct(p1);
        dao.saveProduct(p2);
        dao.saveProduct(p3);

        // 4. Test retrieving active products
        Product[] actives = dao.getActiveProducts();

        System.out.println("--- Active Products List ---");
        for (int i = 0; i < actives.length; i++) {
            if (actives[i] != null) {
                System.out.println("ID: " + actives[i].getId() + " | Name: " + actives[i].getName());
            }
        }

        // 5. Test updating a product
        System.out.println("\n--- Testing Update ---");

        // Simulating data coming from the View/Controller
        Product updatedData = new Product(1, "Mechanical Keyboard RGB", 180.00);
        updatedData.setDescription("Now with RGB lighting!");
        updatedData.setActive(true);

        // Call the update method
        boolean isUpdated = dao.updateProduct(updatedData);
        System.out.println("Update successful? " + isUpdated);

        // 6. Verify if the update actually changed the object in memory
        Product verifiedProduct = dao.findById(1);
        if (verifiedProduct != null) {
            System.out.println("New Name: " + verifiedProduct.getName());
            System.out.println("New Price: " + verifiedProduct.getPrice());
            System.out.println("New Description: " + verifiedProduct.getDescription());
            System.out.println("Updated At: " + verifiedProduct.getUpdatedAt());
        }
    }
}
