import controller.ProductController;
import controller.UserController;
import dao.PersonDao;
import dao.ProductDao;
import dao.UserDao;
import view.MainView;
import view.ProductView;
import view.UserView;

public class Main {
    static void main(String[] args) {
        // 1. Create DAO (Simulate DATABASE)
        ProductDao productDao = new ProductDao(100);
        PersonDao personDao = new PersonDao(100);
        UserDao userDao = new UserDao(100);

        // 2. Create Controllers
        ProductController productController = new ProductController(productDao);
        UserController userController = new UserController(userDao, personDao);

        // 3. Create Views
        ProductView productView = new ProductView(productController);
        UserView userView = new UserView(userController);
        MainView mainView = new MainView(productController, userController, userView);

        // 4. Call test
        mainView.start();
    }
}
