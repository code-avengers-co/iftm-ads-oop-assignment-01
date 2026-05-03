import controller.*;
import dao.*;
import view.*;

public class Main {
    static void main(String[] args) {
        // 1. Create DAO (Simulate DATABASE)
        ProductDao productDao = new ProductDao(100);
        PersonDao personDao = new PersonDao(100);
        UserDao userDao = new UserDao(100);
        CartDao cartDao = new CartDao(100);
        CartItemDao cartItemDao = new CartItemDao(100);
        OrderDao orderDao = new OrderDao(100);
        OrderItemDao orderItemDao = new OrderItemDao(100);
        StockMovementDao stockMovementDao = new StockMovementDao(100);
        CouponDao couponDao = new CouponDao(100);

        // 2. Create Controllers
        ProductController productController = new ProductController(productDao);
        UserController userController = new UserController(userDao, personDao);
        CouponController couponController = new CouponController(couponDao);
        CartController cartController = new CartController(cartDao, cartItemDao, productDao);
        StockController stockController = new StockController(stockMovementDao, productDao);
        OrderController orderController = new OrderController(orderDao);
        ReportController reportController = new ReportController(orderDao);
        DeliveryDao deliveryDao = new DeliveryDao(100);
        TriggerController triggerController = new TriggerController(cartDao, cartItemDao, orderDao, deliveryDao);
        CheckoutController checkoutController = new CheckoutController(
                cartDao,
                cartItemDao,
                orderDao,
                orderItemDao,
                stockMovementDao,
                productDao,
                couponDao,
                deliveryDao
        );

        // 3. Create Views
        ProductView productView = new ProductView(productController);
        CouponView couponView = new CouponView(couponController);
        UserView userView = new UserView(userController);
        CartView cartView = new CartView(cartController, checkoutController, productView);
        OrderView orderView = new OrderView(orderController);
        StockView stockView = new StockView(stockController, productView);
        ReportView reportView = new ReportView(reportController);
        MainView mainView = new MainView(
                userController,
                triggerController,
                userView,
                productView,
                couponView,
                cartView,
                orderView,
                stockView,
                reportView
        );

        // 4. Call test
        mainView.start();
    }
}
