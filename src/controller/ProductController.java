package controller;

import dao.ProductDao;
import model.Product;

import java.time.LocalDateTime;

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
