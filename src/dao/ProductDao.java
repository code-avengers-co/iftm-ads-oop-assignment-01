package dao;

import model.Product;

public class ProductDao {
    private Product[] products;
    private int count; // Current products count

    public ProductDao(int maxProducts) {
        this.products = new Product[maxProducts];
        this.count = 0;
    }

    public boolean saveProduct(Product product){
        // The products array is full
        if (count >= products.length){
            return false;
        }

        // Store the product in the array and increase count
        products[count] = product;
        count++;

        return true;
    }

    public Product[] getActiveProducts(){
        Product[] activeProducts = new Product[count];
        int activeProductsCount = 0;

        for (int i = 0; i < count; i++) {
            if (products[i] != null && products[i].isActive()) {
                activeProducts[activeProductsCount] = products[i];
                activeProductsCount++;
            }
        }

        return activeProducts;
    }
}
