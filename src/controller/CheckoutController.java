package controller;

import dao.*;
import model.*;
import utils.CreationIdUtils;
import utils.UserSession;

public class CheckoutController {
    private CartDao cartDao;
    private CartItemDao cartItemDao;
    private OrderDao orderDao;
    private OrderItemDao orderItemDao;
    private StockMovementDao stockMovementDao;
    private ProductDao productDao;

    public CheckoutController(
            CartDao cartDao,
            CartItemDao cartItemDao,
            OrderDao orderDao,
            OrderItemDao orderItemDao,
            StockMovementDao stockMovementDao,
            ProductDao productDao) {
        this.cartDao = cartDao;
        this.cartItemDao = cartItemDao;
        this.orderDao = orderDao;
        this.orderItemDao = orderItemDao;
        this.stockMovementDao = stockMovementDao;
        this.productDao = productDao;
    }

    public boolean processCheckout(String paymentMethod) {
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

        // 3. Get the total value
        double totalValue = 0;
        for (CartItem item : cartItems) {
            totalValue += item.getQuantity() * item.getUnitPrice();
        }

        // 4. Create the order
        Order newOrder = new Order(CreationIdUtils.generateOrderId(), user, totalValue, paymentMethod);

        boolean orderSaved = orderDao.saveOrder(newOrder);
        if (!orderSaved) {
            return false;
        }

        // 5. Copy CartItems to OrderItems
        for (CartItem cItem : cartItems) {
            OrderItem oItem = new OrderItem(
                    CreationIdUtils.generateOrderItemId(),
                    newOrder,
                    cItem.getProduct(),
                    cItem.getQuantity(),
                    cItem.getUnitPrice()
            );

            orderItemDao.saveOrderItem(oItem);
        }

        // 6. Close cart
        cart.setStatus(model.enums.CartStatus.Closed);
        cartDao.updateCart(cart);

        return true;
    }
}
