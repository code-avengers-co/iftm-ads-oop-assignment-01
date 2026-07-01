package controller;

import dao.CouponDao;
import model.Coupon;

import model.enums.DiscountType;
import java.util.List;

import java.time.LocalDate;

public class CouponController {
    private CouponDao couponDao;

    public CouponController(CouponDao couponDao) { this.couponDao = couponDao; }

    public boolean createCoupon(String code, DiscountType discountType, double discountValue, double minimumPrice, LocalDate expiresAt){
        Coupon newCoupon = new Coupon(code, discountType, discountValue, minimumPrice, expiresAt);

        return couponDao.saveCoupon(newCoupon);
    }


    public List<Coupon> getValidCoupons() {
        return couponDao.getValidCoupons();
    }

    public List<Coupon> getCoupons() {
        return couponDao.getCoupons();
    }

    public boolean deleteCoupon(String code) {
        return couponDao.deleteCoupon(code);
    }
}
