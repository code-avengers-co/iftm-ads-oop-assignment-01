import controller.CouponController;
import dao.CouponDao;
import view.CouponView;

public class main {
    static void main(String[] args) {
        ProductDao dao = new ProductDao(100);
        ProductController controller = new ProductController(dao);
        ProductView view = new ProductView(controller);

        view.showMenu();
    }
}
