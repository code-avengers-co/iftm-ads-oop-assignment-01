package controller;

import dao.CouponDao;
import dao.ProductDao;
import view.CouponView;
import view.ProductView;

public class main {
    static void main(String[] args) {
        //ProductDao dao = new ProductDao(100);
        //ProductController controller = new ProductController(dao);
        //ProductView view = new ProductView(controller);

        //view.showMenu();

        CouponDao couponDao = new CouponDao(100);
        CouponController couponController = new CouponController(couponDao);
        CouponView couponView = new CouponView(couponController);

        couponView.showMenu();
    }
}
