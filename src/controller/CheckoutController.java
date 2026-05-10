package controller;

import dao.*;
import model.*;
import model.enums.DiscountType;
import model.enums.OrderStatus;
import utils.CreationIdUtils;
import utils.SystemClock;
import utils.UserSession;

public class CheckoutController {
    private CartDao cartDao;
    private CartItemDao cartItemDao;
    private OrderDao orderDao;
    private OrderItemDao orderItemDao;
    private StockMovementDao stockMovementDao;
    private ProductDao productDao;
    private CouponDao couponDao;
    private DeliveryDao deliveryDao;

    public CheckoutController(
            CartDao cartDao, CartItemDao cartItemDao, OrderDao orderDao,
            OrderItemDao orderItemDao, StockMovementDao stockMovementDao,
            ProductDao productDao, CouponDao couponDao, DeliveryDao deliveryDao) {
        this.cartDao = cartDao;
        this.cartItemDao = cartItemDao;
        this.orderDao = orderDao;
        this.orderItemDao = orderItemDao;
        this.stockMovementDao = stockMovementDao;
        this.productDao = productDao;
        this.couponDao = couponDao;
        this.deliveryDao = deliveryDao;
    }

    public boolean processCheckout(String paymentMethod, String couponCode) {
        // 1. Check user session
        if (!UserSession.isLoggedIn()) {
            return false;
        }

        User user = UserSession.getLoggedUser();

        // 2. Get the Open Cart for the User
        Cart cart = cartDao.findOpenCartByUser(user.getId());
        if (cart == null) {
            return false; // Not cart open
        }

        CartItem[] cartItems = cartItemDao.findItemsByCartId(cart.getId());
        if (cartItems.length == 0) {
            return false; // Empty cart
        }

        for (CartItem item : cartItems){
            if (item.getQuantity() > item.getProduct().getStockQuantity()){
                return false; // Not enough stock for product
            }
        }

        // 3. Get the total value
        double totalValue = 0;
        for (CartItem item : cartItems) {
            totalValue += item.getQuantity() * item.getUnitPrice();
        }

        // Apply discount if coupon code is valid
        totalValue = calculateTotalWithDiscount(totalValue, couponCode);

        // 4. Create the order and save it
        Order newOrder = new Order(CreationIdUtils.generateOrderId(), user, totalValue, paymentMethod);
        newOrder.setStatus(OrderStatus.Paid);

        boolean orderSaved = orderDao.saveOrder(newOrder);
        if (!orderSaved) {
            return false;
        }

        Delivery newDelivery = new Delivery(CreationIdUtils.generateDeliveryId(), newOrder);
        deliveryDao.saveDelivery(newDelivery);

        // 5. Copy CartItems to OrderItems
        for (CartItem cartItem : cartItems) {
            // 5.1 Create OrderItem and Save it
            OrderItem orderItem = new OrderItem(
                    CreationIdUtils.generateOrderItemId(),
                    newOrder,
                    cartItem.getProduct(),
                    cartItem.getQuantity(),
                    cartItem.getUnitPrice()
            );
            orderItemDao.saveOrderItem(orderItem);

            // 5.2 Create StockMovement and Save it
            StockMovement movement = new StockMovement(
                    CreationIdUtils.generateStockMovementId(),
                    cartItem.getProduct(),
                    cartItem.getQuantity(),
                    model.enums.MovementType.OUT,
                    cartItem.getUnitPrice()
            );
            stockMovementDao.saveStockMovement(movement);

            // 5.3 Update Product Stock
            Product product = cartItem.getProduct();
            int newStock = product.getStockQuantity() - cartItem.getQuantity();
            product.setStockQuantity(newStock);
            productDao.updateProduct(product);
        }

        // 6. Close cart
        cart.setStatus(model.enums.CartStatus.Closed);
        cartDao.updateCart(cart);

        return true;
    }

    private double calculateTotalWithDiscount(double subtotal, String couponCode) {
        if (couponCode == null || couponCode.trim().isEmpty()) {
            return subtotal; // No discount
        }

        Coupon coupon = couponDao.findByCode(couponCode);

        // 1. Check if coupon exists, is active and not expired
        if (coupon == null || !coupon.isActive() || coupon.getExpiresAt().isBefore(SystemClock.today())) {
            return subtotal;
        }

        // 2. Check the minimumPrice of the coupon
        if (coupon.getMinimumPrice() > subtotal) {
            return subtotal;
        }

        // 3 Apply discount
        double finalTotal;
        if (coupon.getType() == model.enums.DiscountType.Fixed){
            finalTotal = subtotal - coupon.getDiscountValue();
        } else {
            finalTotal = subtotal - (subtotal * (coupon.getDiscountValue() / 100.0));
        }

        // 4. Ensure total doesn't go below zero
        return Math.max(0, finalTotal);
    }
}
