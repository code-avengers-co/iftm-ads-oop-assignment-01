package dao;

import model.Coupon;
import model.enums.DiscountType;
import utils.DatabaseConnection;
import utils.SystemClock;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CouponDao {

    public boolean saveCoupon(Coupon coupon) {
        String sql = "INSERT INTO coupon (code, discount_type, discount_value, minimum_price, is_active, expires_at, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        DatabaseConnection factory = new DatabaseConnection();

        try (Connection connection = factory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, coupon.getCode());
            stmt.setString(2, coupon.getType().name());
            stmt.setDouble(3, coupon.getDiscountValue());
            stmt.setDouble(4, coupon.getMinimumPrice());
            stmt.setBoolean(5, coupon.isActive());
            stmt.setDate(6, Date.valueOf(coupon.getExpiresAt()));
            stmt.setTimestamp(7, Timestamp.valueOf(coupon.getCreatedAt()));
            stmt.setTimestamp(8, Timestamp.valueOf(coupon.getUpdatedAt()));

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        coupon.setId(rs.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Coupon findById(int id) {
        String sql = "SELECT * FROM coupon WHERE id = ?";
        DatabaseConnection factory = new DatabaseConnection();

        try (Connection conn = factory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToCoupon(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Coupon findByCode(String code) {
        String sql = "SELECT * FROM coupon WHERE code = ?";
        DatabaseConnection factory = new DatabaseConnection();

        try (Connection conn = factory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, code);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToCoupon(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Coupon> getCoupons() {
        List<Coupon> allCoupons = new ArrayList<>();
        String sql = "SELECT * FROM coupon";

        DatabaseConnection factory = new DatabaseConnection();

        try (Connection conn = factory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                allCoupons.add(mapResultSetToCoupon(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allCoupons;
    }

    public List<Coupon> getValidCoupons() {
        List<Coupon> validCoupons = new ArrayList<>();
        String sql = "SELECT * FROM coupon WHERE is_active = true AND expires_at >= ?";
        
        LocalDate today = SystemClock.today();

        DatabaseConnection factory = new DatabaseConnection();

        try (Connection conn = factory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(today));

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    validCoupons.add(mapResultSetToCoupon(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return validCoupons;
    }

    public boolean deleteCoupon(String code) {
        String sql = "DELETE FROM coupon WHERE code = ?";
        DatabaseConnection factory = new DatabaseConnection();

        try (Connection conn = factory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, code);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    private Coupon mapResultSetToCoupon(ResultSet rs) throws SQLException {
        return new Coupon(
                rs.getInt("id"),
                rs.getString("code"),
                DiscountType.valueOf(rs.getString("discount_type")),
                rs.getDouble("discount_value"),
                rs.getDouble("minimum_price"),
                rs.getBoolean("is_active"),
                rs.getDate("expires_at").toLocalDate(),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getTimestamp("updated_at").toLocalDateTime()
        );
    }
}
