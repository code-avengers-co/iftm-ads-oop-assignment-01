package controller;

import dao.CouponDao;
import model.Coupon;

import java.time.LocalDateTime;

public class CouponController {
    private CouponDao couponDao;

    public CouponController(CouponDao couponDao) { this.couponDao = couponDao; }

    public boolean createCoupon(int id, String code, String discountType, double discountValue, double minimumPrice, LocalDateTime expiresAt){
        Coupon newCoupon = new Coupon(id, code, discountType, discountValue, minimumPrice, expiresAt);

        return couponDao.saveCoupon(newCoupon);
    }
}
