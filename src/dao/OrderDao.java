package dao;

import model.Coupon;
import model.Order;
import model.User;
import model.enums.OrderStatus;
import utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDao {

    public boolean saveOrder(Order order) {
        String sql = "INSERT INTO orders (user_id, coupon_id, status, total_value, payment_method, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?)";
        DatabaseConnection factory = new DatabaseConnection();

        try (Connection connection = factory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, order.getUser().getId());
            if (order.getCoupon() != null) {
                stmt.setInt(2, order.getCoupon().getId());
            } else {
                stmt.setNull(2, Types.INTEGER);
            }
            stmt.setString(3, order.getStatus().name());
            stmt.setDouble(4, order.getTotalValue());
            stmt.setString(5, order.getPaymentMethod());
            stmt.setTimestamp(6, Timestamp.valueOf(order.getCreatedAt()));
            stmt.setTimestamp(7, Timestamp.valueOf(order.getUpdatedAt()));

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        order.setId(rs.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Order> getAllOrders() {
        List<Order> allOrders = new ArrayList<>();
        String sql = "SELECT * FROM orders";
        DatabaseConnection factory = new DatabaseConnection();

        try (Connection conn = factory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                allOrders.add(mapResultSetToOrder(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allOrders;
    }

    public Order findById(int id) {
        String sql = "SELECT * FROM orders WHERE id = ?";
        DatabaseConnection factory = new DatabaseConnection();

        try (Connection conn = factory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToOrder(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Order> findOrdersByUserId(int userId) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE user_id = ?";
        DatabaseConnection factory = new DatabaseConnection();

        try (Connection conn = factory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    orders.add(mapResultSetToOrder(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public boolean updateOrder(Order order) {
        String sql = "UPDATE orders SET status = ?, coupon_id = ?, total_value = ?, payment_method = ?, updated_at = ? WHERE id = ?";
        DatabaseConnection factory = new DatabaseConnection();

        try (Connection conn = factory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, order.getStatus().name());
            if (order.getCoupon() != null) {
                stmt.setInt(2, order.getCoupon().getId());
            } else {
                stmt.setNull(2, Types.INTEGER);
            }
            stmt.setDouble(3, order.getTotalValue());
            stmt.setString(4, order.getPaymentMethod());
            stmt.setTimestamp(5, Timestamp.valueOf(order.getUpdatedAt()));
            stmt.setInt(6, order.getId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteOrder(int id) {
        String sql = "DELETE FROM orders WHERE id = ?";
        DatabaseConnection factory = new DatabaseConnection();

        try (Connection conn = factory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Order mapResultSetToOrder(ResultSet rs) throws SQLException {
        PersonDao personDao = new PersonDao();
        UserDao userDao = new UserDao(personDao);
        CouponDao couponDao = new CouponDao();

        User user = userDao.findById(rs.getInt("user_id"));
        
        Coupon coupon = null;
        int couponId = rs.getInt("coupon_id");
        if (!rs.wasNull()) {
            coupon = couponDao.findById(couponId);
        }

        return new Order(
                rs.getInt("id"),
                user,
                coupon,
                OrderStatus.valueOf(rs.getString("status")),
                rs.getDouble("total_value"),
                rs.getString("payment_method"),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getTimestamp("updated_at").toLocalDateTime()
        );
    }
}