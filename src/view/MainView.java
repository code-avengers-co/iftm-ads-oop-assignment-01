package view;

import controller.*;
import model.User;
import utils.InputUtils;
import utils.UserSession;

public class MainView {
    private UserController userController;
    private TriggerController triggerController;
    private UserView userView;
    private ProductView productView;
    private CouponView couponView;
    private CartView cartView;
    private OrderView orderView;
    private StockView stockView;
    private ReportView reportView;
    private DeliveryView deliveryView;

    public MainView(
            UserController userController,
            TriggerController triggerController,
            UserView userView,
            ProductView productView,
            CouponView couponView,
            CartView cartView,
            OrderView orderView,
            StockView stockView,
            ReportView reportView,
            DeliveryView deliveryView) {
        this.userController = userController;
        this.triggerController = triggerController;
        this.userView = userView;
        this.productView = productView;
        this.couponView = couponView;
        this.cartView = cartView;
        this.orderView = orderView;
        this.stockView = stockView;
        this.reportView = reportView;
        this.deliveryView = deliveryView;
    }

    public void start() {
        System.out.println("=== WELCOME TO THE STORE ===");

        // 1. Show the Products
        productView.showAvailableProducts();

        // 2. Show the menu based on login status
        int option;
        do {
            if (!UserSession.isLoggedIn()) {
                option = showGuestMenu();
            } else if (UserSession.getLoggedUser().isAdmin()) {
                option = showAdminMenu();
            } else {
                option = showLoggedMenu();
            }
        } while (option != 0);
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
        System.out.println("System Date: " + utils.SystemClock.today());
        System.out.println("1 - Manage Products");
        System.out.println("2 - Manage Coupons");
        System.out.println("3 - Manual Stock Entry");
        System.out.println("4 - Manage Users");
        System.out.println("5 - View Reports");
        System.out.println("6 - Advance System Time (Calendar)");
        System.out.println("7 - Manage Deliveries");
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
                stockView.handleManualStockEntry();
                break;
            case 4:
                userView.showMenu();
                break;
            case 5:
                reportView.showMenu();
                break;
            case 6:
                int days = InputUtils.readInt("How many days do you want to advance? ", 1, 365);
                utils.SystemClock.advanceDays(days);

                triggerController.runTriggers();

                System.out.println("Background processes executed! Carts checked and Orders updated.");
                break;
            case 7:
                deliveryView.showMenu();
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
        System.out.println("1 - Shop & Cart");
        System.out.println("2 - My Orders History");
        System.out.println("9 - Logout");
        System.out.println("0 - Exit");

        int option = InputUtils.readInt("Choose an option: ", 0, 9);

        switch (option) {
            case 1:
                cartView.showMenu();
                break;
            case 2:
                orderView.showMyOrders();
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

    private void handleCreateAccount() {
        userView.renderRegisterUser();
    }
}