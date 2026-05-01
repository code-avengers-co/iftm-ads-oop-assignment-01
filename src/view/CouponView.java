package view;

import controller.CouponController;

import java.util.Scanner;

public class CouponView {
    private CouponController couponController;
    private Scanner scanner;

    public CouponView(CouponController controller) {
        this.couponController = controller;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        String menu = """
                COUPON MENU
                1 - Create Coupon
                2 - List Available Coupons
                0 - Exit
                """;
        System.out.println(menu);

        int option = -1;
        do {
            System.out.println("Choose an Option ...:");
            option = Integer.parseInt(scanner.nextLine());
        } while (option < 0 || option > 2);

        switch (option) {
            case 1:
                renderCreateCoupon();
                break;
            case 2:
                renderListOfAllCoupons();
                break;
            case 3:
                System.out.println("Exiting the Program ...");
                break;
        }

    }

    public void renderCreateCoupon() {
        System.out.println("Cupom Criado");
    };

    public void renderListOfAllCoupons() {
        System.out.println("Cupom Criado");
    };

}
