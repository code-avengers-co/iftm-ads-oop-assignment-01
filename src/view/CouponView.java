package view;

import controller.CouponController;
import model.Coupon;
import model.enums.DiscountType;
import utils.InputUtils;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class CouponView {
    private CouponController couponController;
    private Scanner scanner;

    public CouponView(CouponController controller) {
        this.couponController = controller;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        int option;
        do {
            option = getOption();
            switch (option) {
                case 1:
                    renderCreateCoupon();
                    break;
                case 2:
                    renderListOfAllCoupons();
                    break;
                case 3:
                    renderDeleteCoupon();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
            }
        } while (option != 0);
    }

    private int getOption() {
        String menu = """
                --- COUPON MENU ---
                1 - Register Coupon
                2 - List of all Coupons
                3 - Delete Coupon
                0 - Back
                """;
        System.out.println(menu);

        return InputUtils.readInt("Choose an option: ", 0, 3);
    }

    private void renderCreateCoupon() {
        System.out.println("Enter Coupon Details");

        System.out.println("Coupon Code: ");
        String code = scanner.nextLine();

        System.out.println("\nSelect Discount Type:");
        System.out.println("1 - Fixed (R$)");
        System.out.println("2 - Percentual (%)");
        int typeOption = InputUtils.readInt("Choose an option: ", 1, 2);

        DiscountType discountType = (typeOption == 1) ? DiscountType.Fixed : DiscountType.Percentual;

        System.out.println("Discount Value: ");
        double discountValue = Double.parseDouble(scanner.nextLine());

        System.out.println("The Minimum Price to Apply: ");
        double minimumPrice = Double.parseDouble(scanner.nextLine());

        LocalDate expiresAt = null;
        while (expiresAt == null) {
            System.out.print("Coupon expires date (YYYY-MM-DD): ");
            String expiresInput = scanner.nextLine();
            try {
                expiresAt = LocalDate.parse(expiresInput);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format! Please use YYYY-MM-DD (e.g., 2000-05-25).");
            }
        }

        boolean succes = couponController.createCoupon(code, discountType, discountValue, minimumPrice, expiresAt);

        if (succes) {
            System.out.println("Product saved successfully!");
        } else {
            System.out.println("Error: Database is full.");
        }
    };

    private void renderListOfAllCoupons() {
        System.out.println("Listing all Coupons");
        Coupon[] allCoupons = couponController.getCoupons();

        if (allCoupons.length == 0) {
            System.out.println("No Coupons Founded");
            return;
        }

        showCouponList("", allCoupons);
    }

    private void renderDeleteCoupon() {
        Coupon[] activeCoupons = couponController.getValidCoupons();

        if (activeCoupons.length == 0) {
            System.out.println("--- DELETE COUPON ---");
            System.out.println("No valid coupons available to delete.");
            return;
        }

        showCouponList("--- DELETE COUPON ---\nAvailable Coupons:", activeCoupons);
        System.out.print("Enter the Code of the coupon to delete: ");
        String code = scanner.nextLine();

        System.out.println("Are you sure you want to delete this coupon? (Y/N)");
        String confirmation = scanner.nextLine();

        if (confirmation.equalsIgnoreCase("Y")) {
            boolean isDeleted = couponController.deleteCoupon(code);
            if (isDeleted) {
                System.out.println("Coupon deleted successfully!");
            } else {
                System.out.println("Error: Coupon not found.");
            }
        } else {
            System.out.println("Coupon deletion cancelled.");
        }
    }

    void showCouponList(String title, Coupon[] coupons) {
        if (title != null && !title.isEmpty()) {
            System.out.println(title);
        }

        if (coupons.length == 0){
            System.out.println("No Coupons Founded");
            return;
        }

        for (Coupon coupon : coupons) {
            if (coupon != null) {
                System.out.println(coupon.getCode());
            }
        }
    }
}
