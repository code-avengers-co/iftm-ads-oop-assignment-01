package controller;

import dao.ProductDao;
import model.Product;

public class ProductController {
    private ProductDao productDao;

    public ProductController(ProductDao productDao){
        this.productDao = productDao;
    }

    public boolean registerProduct(int id, String name, double price) {
        // 1. Create the product
        Product newProduct = new Product(id, name, price);

        // 2. Send to DAO
        return productDao.saveProduct(newProduct);
    }

    public Product[] getActiveProducts() {
        return productDao.getActiveProducts();
    }
}
