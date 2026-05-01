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

    public boolean saveCoupon(Coupon coupon){
        if (count >= coupons.length){
            return false;
        }

        coupons[count] = coupon;
        count++;

        return true;
    }

}
