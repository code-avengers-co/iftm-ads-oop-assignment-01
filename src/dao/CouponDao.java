package dao;

import model.Coupon;
import model.Product;

import java.time.LocalDateTime;

public class CouponDao {
    private Coupon[] coupons;
    private int count;

    public CouponDao(int maxCoupons) {
        this.coupons = new Coupon[maxCoupons];
        this.count = 0;
    }

    public boolean saveCoupon(Coupon coupon) {
        if (count >= coupons.length){
            return false;
        }

        coupons[count] = coupon;
        count++;

        return true;
    }

    public Coupon findById(int id) {
        for (int i = 0; i < count; i++) {
            if (coupons[i].getId() == id) {
                return coupons[i];
            }
        }

        return null;
    }

    public Coupon[] getCoupons() {
        Coupon[] allCoupons= new Coupon[count];
        int currentIndex = 0;

        for (int i = 0; i < count; i++) {
            if (coupons[i] != null) {
                allCoupons[currentIndex] = coupons[i];
                currentIndex++;
            }
        }

        return allCoupons;
    }

    public Coupon[] getValidCoupons() {
        Coupon[] validCoupons = new Coupon[count];
        int validCouponsCount = 0;

        for (int i = 0; i < count; i++) {
            if (coupons[i] != null && coupons[i].isActive()) {
                validCoupons[validCouponsCount] = coupons[i];
                validCouponsCount++;
            }
        }

        return validCoupons;
    }

    public boolean deleteCoupon(String code) {
        int indexToDeleted = -1;
        for (int i = 0; i< count; i++) {
            if (this.coupons[i].getCode() == code) {
                indexToDeleted = i;
                break;
            }
        }

        if (indexToDeleted == -1) {
            return false;
        }

        coupons[indexToDeleted] = coupons[count - 1];
        coupons[count - 1] = null;
        count--;

        return true;
    }
}
