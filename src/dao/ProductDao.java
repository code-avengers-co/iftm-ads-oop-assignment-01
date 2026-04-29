package dao;

import model.Product;

public class ProductDao {
    private Product[] products;

    public ProductDao(int maxProducts) {
        this.products = new Product[maxProducts];
    }

    public Product[] getProducts() {
        return products;
    }
}
