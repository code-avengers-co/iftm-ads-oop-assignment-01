package view;

import controller.CartController;
import controller.CheckoutController;
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
    private CheckoutController checkoutController;
    private UserView userView;
    private ProductView productView;
    private CouponView couponView;

    public MainView(
            ProductController productController, UserController userController,
            CartController cartController, CheckoutController checkoutController,
            UserView userView, ProductView productView, CouponView couponView) {
        this.productController = productController;
        this.userController = userController;
        this.cartController = cartController;
        this.checkoutController = checkoutController;
        this.userView = userView;
        this.productView = productView;
        this.couponView = couponView;
    }

    public void start() {
        System.out.println("=== WELCOME TO THE STORE ===");

        // 1. Show the Products
        showAvailableProducts();

        // 2. Show the menu based on login status
        int option;
        do {
            if (!UserSession.isLoggedIn()) {
                option = showGuestMenu();
            } else if (UserSession.getLoggedUser().isAdmin()){
                option = showAdminMenu();
            } else {
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

    private int showAdminMenu() {
        System.out.println("\n=== ADMIN PANEL ===");
        System.out.println("Welcome, Boss " + UserSession.getLoggedUser().getPerson().getName() + "!");
        System.out.println("1 - Manage Products");
        System.out.println("2 - Manage Coupons");
        System.out.println("3 - Manual Stock Entry (Entrada de Estoque)");
        System.out.println("4 - Manage Users");
        System.out.println("9 - Logout");
        System.out.println("0 - Exit");

        int option = InputUtils.readInt("Choose an option: ", 0, 9);

        switch (option) {
            case 1:
                productView.showMenu();
                break;
            case 2:
                couponView.showMenu();
                break;
            case 3:
                // handleManualStockEntry();
                System.out.println("Manual Stock Entry feature is not implemented yet.");
                break;
            case 4:
                userView.showMenu();
                break;
            case 9:
                UserSession.logout();
                System.out.println("Admin logged out successfully.");
                break;
            case 0:
                System.out.println("Exiting... Goodbye Boss!");
                break;
            default:
                System.out.println("Invalid Option");
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
        String username = InputUtils.readString("Username: ");
        String password = InputUtils.readString("Password: ");

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

        int productId = InputUtils.readInt("Enter Product ID to add (-1 to cancel): ", -1, Integer.MAX_VALUE);
        if (productId == -1){
            System.out.println("Cancelled adding to cart.");
            return;
        }

        int quantity = InputUtils.readInt("Enter Quantity (-1 to cancel): ", -1, Integer.MAX_VALUE);
        if (quantity == -1){
            System.out.println("Cancelled adding to cart.");
            return;
        }

        boolean success = cartController.addProductToCart(productId, quantity);
        if (success) {
            System.out.println("Success! Item added to your cart.");
        } else {
            System.out.println("Error: Could not add item. Check if the Product ID is valid.");
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
            System.out.println("ID: " + item.getProduct().getId() +
                    " | Product: " + item.getProduct().getName() +
                    " | Qty: " + item.getQuantity() +
                    " | Unit Price: R$" + String.format("%.2f", item.getUnitPrice()) +
                    " | Subtotal: R$" + String.format("%.2f", subtotal));
        }

        double total = cartController.getLoggedUserCartTotal();
        System.out.println("-------------------------");
        System.out.println("TOTAL: R$" + String.format("%.2f", total));;

        System.out.println("\n1 - Checkout (Finish Order)");
        System.out.println("2 - Remove Item from Cart");
        System.out.println("0 - Back to Menu");

        int option = InputUtils.readInt("Choose an option: ", 0, 2);
        switch (option){
            case 1:
                handleCheckout();
                break;
            case 2:
                handleRemoveFromCart();
                break;
        }
    }

    private void handleCheckout() {
        System.out.println("\n--- CHECKOUT ---");

        String paymentMethod = InputUtils.readString("Enter Payment Method (e.g., PIX, Credit Card): ");

        String hasCoupon = InputUtils.readString("Do you have a discount coupon? (Y/N)");
        String couponCode = null;

        if (hasCoupon.equalsIgnoreCase("Y")) {
            couponCode = InputUtils.readString("Enter Coupon Code: ");
        }

        boolean success = checkoutController.processCheckout(paymentMethod, couponCode);

        if (success) {
            System.out.println("\nSuccess! Your order has been placed.");
            System.out.println("The stock was updated and your cart is now empty.");
        } else {
            System.out.println("\nError: Could not process checkout.");
            System.out.println("Please check if the items are currently in stock.");
        }
    }

    private void handleRemoveFromCart() {
        System.out.println("\n--- REMOVE ITEM ---");
        int productId = InputUtils.readInt("Enter the Product ID to remove: ", 1, Integer.MAX_VALUE);

        boolean success = cartController.removeProductFromCart(productId);

        if (success) {
            System.out.println("Item successfully removed from your cart.");
        } else {
            System.out.println("Error: Product not found in your cart.");
        }
    }
}