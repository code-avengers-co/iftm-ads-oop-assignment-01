package controller;

import dao.CouponDao;
import model.Coupon;
import model.Product;

import java.time.LocalDateTime;

public class CouponController {
    private CouponDao couponDao;

    public CouponController(CouponDao couponDao) { this.couponDao = couponDao; }

    public boolean createCoupon(int id, String code, String discountType, double discountValue, double minimumPrice, LocalDateTime expiresAt){
        Coupon newCoupon = new Coupon(id, code, discountType, discountValue, minimumPrice, expiresAt);

        return couponDao.saveCoupon(newCoupon);
    }

    public Coupon findByid(int id) { return couponDao.findById(id); }

    public Coupon[] getValidCoupons() {
        return couponDao.getValidCoupons();
    }

    public Coupon[] getCoupons() { return couponDao.getCoupons(); }

    public boolean deleteCoupon(String code) { return couponDao.deleteCoupon(code); }
}
