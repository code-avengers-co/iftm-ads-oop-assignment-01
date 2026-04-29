package controller;

import dao.ProductDao;
import view.ProductView;

public class main {
    static void main(String[] args) {
        ProductDao dao = new ProductDao(100);
        ProductController controller = new ProductController(dao);
        ProductView view = new ProductView(controller);

        view.showMenu();
    }
}
