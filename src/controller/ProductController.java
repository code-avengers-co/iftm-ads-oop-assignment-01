package controller;

import dao.ProductDao;
import java.util.Scanner;

public class ProductController {
    private ProductDao productDao;
    private Scanner scanner;

    public ProductController(ProductDao productDao){
        this.productDao = productDao;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu(){

    }
}
