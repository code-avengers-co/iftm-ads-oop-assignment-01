package view;

import controller.ProductController;
import controller.UserController;
import model.Product;
import model.User;
import utils.UserSession;

import java.util.Scanner;

public class MainView {
    private ProductController productController;
    private UserController userController;
    private UserView userView;
    private Scanner scanner;

    public MainView(ProductController productController, UserController userController, UserView userView) {
        this.productController = productController;
        this.userController = userController;
        this.userView = userView;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("=== WELCOME TO THE STORE ===");

        // 1. Show the Products
        showAvailableProducts();

        // 2. Show the menu based on login status
        int option;
        do {
            // If not logged in, show options to log in or create account
            if (!UserSession.isLoggedIn()) {
                option = showGuestMenu();
            } else {
                // If logged in, show options to view products, add to cart, etc.
                option = showLoggedMenu();
            }
        } while (option != 0);
    }

    private void showAvailableProducts() {
        System.out.println("--- PRODUCTS ON SALE ---");

        Product[] products = productController.getActiveProducts();
        for (Product p : products) {
            System.out.println(p);
        }

        System.out.println("------------------------\n");
    }

    private int showGuestMenu() {
        System.out.println("1 - Login");
        System.out.println("2 - Create Account");
        System.out.println("0 - Exit");
        System.out.print("Choose an option: ");

        int option = Integer.parseInt(scanner.nextLine());
        switch (option) {
            case 1:
                handleLogin();
                break;
            case 2:
                handleCreateAccount();
                break;
            case 0:
                System.out.println("Exiting... Goodbye Human!");
                break;
            default:
                System.out.println("Invalid option. Try again.");
        }

        return option;
    }

    private int showLoggedMenu() {
        System.out.println("\nWelcome, " + UserSession.getLoggedUser().getPerson().getName() + "!");
        System.out.println("1 - Add item to Cart");
        System.out.println("2 - View Cart");
        System.out.println("9 - Logout");
        System.out.println("0 - Exit");
        System.out.print("Choose an option: ");

        return Integer.parseInt(scanner.nextLine());
    }

    private void handleLogin() {
        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        User authenticatedUser = userController.authenticate(username, password);

        if (authenticatedUser != null) {
            UserSession.login(authenticatedUser);
            System.out.println("Login successful!");
        } else {
            System.out.println("Invalid credentials. Try again.");
        }
    }

    private void handleCreateAccount(){
        userView.renderRegisterUser();
    }
}