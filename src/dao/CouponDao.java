package dao;

import model.Coupon;
import model.Product;

import java.time.LocalDate;
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

    public Coupon findByCode(String code){
        for (int i = 0; i < count; i++){
            if (coupons[i].getCode().equals(code)){
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
        Coupon[] tempCoupons = new Coupon[count];
        int validCouponsCount = 0;

        LocalDate today = LocalDate.now();

        for (int i = 0; i < count; i++) {
            if (coupons[i].isActive() && !coupons[i].getExpiresAt().isBefore(today)) {
                tempCoupons[validCouponsCount] = coupons[i];
                validCouponsCount++;
            }
        }

        Coupon[] exactCoupons = new Coupon[validCouponsCount];
        for (int i = 0; i < validCouponsCount; i++) {
            exactCoupons[i] = tempCoupons[i];
        }

        return exactCoupons;
    }

    public boolean deleteCoupon(String code) {
        int indexToDeleted = -1;
        for (int i = 0; i< count; i++) {
            if (this.coupons[i].getCode().equals(code)) {
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
