package controller;

import dao.ProductDao;
import model.Product;


public class ProductController {
    private ProductDao productDao;

    public ProductController(ProductDao productDao){
        this.productDao = productDao;
    }

    public boolean registerProduct(String name, double price) {
        // 1. Create the product (ID is auto-generated in constructor)
        Product newProduct = new Product(name, price);

        // 2. Send to DAO
        return productDao.saveProduct(newProduct);
    }

    public Product[] getActiveProducts() {
        return productDao.getActiveProducts();
    }


    public boolean hasProduct(int productId){
        return productDao.findById(productId) != null;
    }

    public boolean updateProduct(int id, String name, double price) {
        Product product = productDao.findById(id);
        if (product == null) {
            return false;
        }

        product.setName(name);
        product.setPrice(price);

        return productDao.updateProduct(product);
    }

    public boolean deleteProduct(int id){
        return productDao.deleteProduct(id);
    }
}
