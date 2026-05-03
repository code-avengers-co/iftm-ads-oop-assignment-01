import controller.CartController;
import controller.CheckoutController;
import controller.ProductController;
import controller.UserController;
import dao.*;
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

        // 2. Create Controllers
        ProductController productController = new ProductController(productDao);
        UserController userController = new UserController(userDao, personDao);
        CartController cartController = new CartController(cartDao, cartItemDao, productDao);
        CheckoutController checkoutController = new CheckoutController(
                cartDao, cartItemDao,
                orderDao, orderItemDao, stockMovementDao, productDao
        );

        // 3. Create Views
        ProductView productView = new ProductView(productController);
        UserView userView = new UserView(userController);
        MainView mainView = new MainView(productController, userController, cartController, checkoutController, userView);

        // 4. Call test
        mainView.start();
    }
}
