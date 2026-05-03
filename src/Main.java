import controller.*;
import dao.*;
import view.CouponView;
import view.MainView;
import view.ProductView;
import view.UserView;

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
        CheckoutController checkoutController = new CheckoutController(
                cartDao, cartItemDao, orderDao, orderItemDao,
                stockMovementDao, productDao, couponDao
        );

        // 3. Create Views
        ProductView productView = new ProductView(productController);
        CouponView couponView = new CouponView(couponController);
        UserView userView = new UserView(userController);

        MainView mainView = new MainView(
                productController, userController,
                cartController, checkoutController,
                userView, productView, couponView
        );

        // 4. Call test
        mainView.start();
    }
}
