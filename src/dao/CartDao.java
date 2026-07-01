package dao;

import model.Cart;
import model.User;
import model.enums.CartStatus;
import utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartDao {

    public boolean saveCart(Cart cart) {
        String sql = "INSERT INTO cart (user_id, status, created_at, updated_at) VALUES (?, ?, ?, ?)";
        DatabaseConnection factory = new DatabaseConnection();

        try (Connection connection = factory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, cart.getUser().getId());
            stmt.setString(2, cart.getStatus().name());
            stmt.setTimestamp(3, Timestamp.valueOf(cart.getCreatedAt()));
            stmt.setTimestamp(4, Timestamp.valueOf(cart.getUpdatedAt()));

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        cart.setId(rs.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Cart> getAllCarts() {
        List<Cart> allCarts = new ArrayList<>();
        String sql = "SELECT * FROM cart";
        DatabaseConnection factory = new DatabaseConnection();

        try (Connection conn = factory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                allCarts.add(mapResultSetToCart(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allCarts;
    }

    public Cart findById(int id) {
        String sql = "SELECT * FROM cart WHERE id = ?";
        DatabaseConnection factory = new DatabaseConnection();

        try (Connection conn = factory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToCart(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Cart findOpenCartByUser(int userId) {
        String sql = "SELECT * FROM cart WHERE user_id = ? AND status = ?";
        DatabaseConnection factory = new DatabaseConnection();

        try (Connection conn = factory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setString(2, CartStatus.Open.name());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToCart(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateCart(Cart cart) {
        String sql = "UPDATE cart SET status = ?, updated_at = ? WHERE id = ?";
        DatabaseConnection factory = new DatabaseConnection();

        try (Connection conn = factory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cart.getStatus().name());
            stmt.setTimestamp(2, Timestamp.valueOf(cart.getUpdatedAt()));
            stmt.setInt(3, cart.getId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Cart mapResultSetToCart(ResultSet rs) throws SQLException {
        PersonDao personDao = new PersonDao();
        UserDao userDao = new UserDao(personDao);
        User user = userDao.findById(rs.getInt("user_id"));

        return new Cart(
                rs.getInt("id"),
                user,
                CartStatus.valueOf(rs.getString("status")),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getTimestamp("updated_at").toLocalDateTime()
        );
    }
}
