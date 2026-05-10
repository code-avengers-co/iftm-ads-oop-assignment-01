package dao;

import model.Product;
import utils.SystemClock;

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

    public Product[] getAllProducts(){
        Product[] allProducts = new Product[count];
        int currentIndex = 0;

        for (int i = 0; i < count; i++) {
            if (products[i] != null) {
                allProducts[currentIndex] = products[i];
                currentIndex++;
            }
        }

        return allProducts;
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

    public Product findById(int id) {
        for (int i = 0; i < count; i++) {
            if (products[i].getId() == id) {
                return products[i];
            }
        }

        return null;
    }

    public boolean updateProduct(Product updatedProduct){
        Product product = findById(updatedProduct.getId());
        if (product == null){
            return false;
        }

        product.setName(updatedProduct.getName());
        product.setDescription(updatedProduct.getDescription());
        product.setPrice(updatedProduct.getPrice());
        product.setActive(updatedProduct.isActive());
        product.setStockQuantity(updatedProduct.getStockQuantity());
        product.setUpdatedAt(SystemClock.now());

        return true;
    }

    public boolean deleteProduct(int id){
        Product product = findById(id);
        if (product == null){
            return false;
        }

        product.setActive(false);
        return true;
    }
}
