import controller.*;
import dao.*;
import view.*;

public class Main {
    static void main(String[] args) {
        // 1. Create DAO instances
        ProductDao productDao = new ProductDao();
        PersonDao personDao = new PersonDao();
        UserDao userDao = new UserDao(personDao);
        CartDao cartDao = new CartDao();
        CartItemDao cartItemDao = new CartItemDao();
        OrderDao orderDao = new OrderDao();
        OrderItemDao orderItemDao = new OrderItemDao();
        StockMovementDao stockMovementDao = new StockMovementDao();
        CouponDao couponDao = new CouponDao();
        DeliveryDao deliveryDao = new DeliveryDao();

        // 2. Create Controllers
        ProductController productController = new ProductController(productDao);
        UserController userController = new UserController(userDao, personDao);
        CouponController couponController = new CouponController(couponDao);
        CartController cartController = new CartController(cartDao, cartItemDao, productDao);
        StockController stockController = new StockController(stockMovementDao, productDao);
        OrderController orderController = new OrderController(orderDao);
        ReportController reportController = new ReportController(orderDao);
        DeliveryController deliveryController = new DeliveryController(deliveryDao);
        TriggerController triggerController = new TriggerController();
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
        DeliveryView deliveryView = new DeliveryView(deliveryController);
        MainView mainView = new MainView(
                userController,
                triggerController,
                userView,
                productView,
                couponView,
                cartView,
                orderView,
                stockView,
                reportView,
                deliveryView
        );

        // 4. Start the application
        mainView.start();
    }
}
