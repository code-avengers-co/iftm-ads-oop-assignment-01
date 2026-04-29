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
        int option;
        do {
            option = getOption();
            switch (option){
                case 1:
                    System.out.println("Creating a new product...");
                    break;
                case 2:
                    System.out.println("Listing all products...");
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
            }

        } while (option != 0);
    }

    private int getOption(){
        String menu = """
                --- PRODUCT MENU ---
                1 - Register Product
                2 - List Products
                0 - Back
                """;
        System.out.print(menu);

        int option = -1;
        do{
            System.out.print("Choose an option: ");
            option = Integer.parseInt(scanner.nextLine());

        } while (option < 0 || option > 2);

        return option;
    }
}
