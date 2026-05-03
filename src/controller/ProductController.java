package controller;

import dao.ProductDao;
import model.Product;

import java.time.LocalDateTime;

public class ProductController {
    private ProductDao productDao;

    public ProductController(ProductDao productDao){
        this.productDao = productDao;

        // Mock Products
        Product product1 = new Product(1, "Laptop", 999.99);
        product1.setStockQuantity(10);
        productDao.saveProduct(product1);

        Product product2 = new Product(2, "Smartphone", 499.99);
        product2.setStockQuantity(20);
        productDao.saveProduct(product2);

        Product product3 = new Product(3, "Headphones", 199.99);
        product3.setStockQuantity(15);
        productDao.saveProduct(product3);

        Product product4 = new Product(4, "Smartwatch", 299.99);
        product4.setStockQuantity(25);
        productDao.saveProduct(product4);
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

    public Product[] getAllProducts(){
        return productDao.getAllProducts();
    }

    public boolean hasProduct(int productId){
        return productDao.findById(productId) != null;
    }

    public boolean updateProduct(int id, String name, double price) {
        Product updatedData = new Product(id, name, price);

        return productDao.updateProduct(updatedData);
    }

    public boolean deleteProduct(int id){
        return productDao.deleteProduct(id);
    }
}
