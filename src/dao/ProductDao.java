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
        if (count + 1 == products.length){
            return false;
        }

        // Storage the product on array and increase count +1
        products[count] = product;
        count++;

        return true;
    }
}
