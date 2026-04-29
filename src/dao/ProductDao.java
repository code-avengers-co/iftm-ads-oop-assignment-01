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
        if (count + 1 == products.length){
            return false;
        }

        products[count] = product;

        return true;
    }
}
