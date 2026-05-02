package view;

import controller.CartController;
import controller.ProductController;
import controller.UserController;
import model.CartItem;
import model.Product;
import model.User;
import utils.InputUtils;
import utils.UserSession;

import java.util.Scanner;

public class MainView {
    private ProductController productController;
    private UserController userController;
    private CartController cartController;
    private UserView userView;
    private Scanner scanner;

    public MainView(ProductController productController, UserController userController, CartController cartController, UserView userView) {
        this.productController = productController;
        this.userController = userController;
        this.cartController = cartController;
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
        System.out.println("--- PRODUCTS ON SALE ------------------------------------------");

        Product[] products = productController.getActiveProducts();
        for (Product p : products) {
            System.out.println(p);
        }

        System.out.println("---------------------------------------------------------------");
    }

    private int showGuestMenu() {
        String menu = """ 
                      1 - Login
                      2 - Create Account
                      0 - Exit
                      """;
        System.out.printf(menu);

        int option = InputUtils.readInt("Choose an option: ", 0, 2);
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

        int option = InputUtils.readInt("Choose an option: ", 0, 9);

        switch (option) {
            case 1:
                handleAddToCart();
                break;
            case 2:
                handleViewCart();
                break;
            case 9:
                UserSession.logout();
                System.out.println("Logged out successfully.");
                break;
            case 0:
                System.out.println("Exiting... Goodbye Human!");
                break;
            default:
                System.out.println("Invalid option. Try again.");
        }

        return option;
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

    private void handleAddToCart() {
        System.out.println("\n--- ADD TO CART ---");

        showAvailableProducts();

        try {
            System.out.print("Enter Product ID to add: ");
            int productId = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter Quantity: ");
            int quantity = Integer.parseInt(scanner.nextLine());

            boolean success = cartController.addProductToCart(productId, quantity);

            if (success) {
                System.out.println("Success! Item added to your cart.");
            } else {
                System.out.println("Error: Could not add item. Check if the Product ID is valid.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter only numbers.");
        }
    }

    private void handleViewCart() {
        System.out.println("\n--- YOUR CART ---");

        CartItem[] items = cartController.getLoggedUserCartItems();

        if (items.length == 0) {
            System.out.println("Your cart is empty.");
            return;
        }

        for (CartItem item : items) {
            double subtotal = item.getQuantity() * item.getUnitPrice();
            System.out.println("Product: " + item.getProduct().getName() +
                    " | Qty: " + item.getQuantity() +
                    " | Unit Price: R$" + item.getUnitPrice() +
                    " | Subtotal: R$" + subtotal);
        }

        double total = cartController.getLoggedUserCartTotal();
        System.out.println("-------------------------");
        System.out.println("TOTAL: R$" + total);
    }
}