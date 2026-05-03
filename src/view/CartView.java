package view;

import controller.CartController;
import controller.CheckoutController;
import controller.ProductController;
import model.CartItem;
import utils.InputUtils;

public class CartView {
    private CartController cartController;
    private CheckoutController checkoutController;
    private ProductController productController;

    public CartView(CartController cartController, CheckoutController checkoutController, ProductController productController) {
        this.cartController = cartController;
        this.checkoutController = checkoutController;
        this.productController = productController;
    }

    public void showMenu() {
        int option;
        do {
            System.out.println("\n--- SHOPPING & CART ---");
            System.out.println("1 - View Products & Add to Cart");
            System.out.println("2 - View My Cart & Checkout");
            System.out.println("0 - Back to Main Menu");

            option = InputUtils.readInt("Choose an option: ", 0, 2);

            switch (option) {
                case 1:
                    handleAddToCart();
                    break;
                case 2:
                    handleViewCart();
                    break;
                case 0:
                    break;
            }
        } while (option != 0);
    }

    private void handleAddToCart() {
        System.out.println("\n--- ADD TO CART ---");

        productController.showAvailableProducts();

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