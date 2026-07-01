package dao;

import model.Cart;
import model.CartItem;
import model.Product;
import utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartItemDao {

    public boolean saveCartItem(CartItem cartItem) {
        String sql = "INSERT INTO cart_item (cart_id, product_id, quantity, unit_price) VALUES (?, ?, ?, ?)";
        DatabaseConnection factory = new DatabaseConnection();

        try (Connection connection = factory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, cartItem.getCart().getId());
            stmt.setInt(2, cartItem.getProduct().getId());
            stmt.setInt(3, cartItem.getQuantity());
            stmt.setDouble(4, cartItem.getUnitPrice());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        cartItem.setId(rs.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<CartItem> getAllCartItems() {
        List<CartItem> allCartItems = new ArrayList<>();
        String sql = "SELECT * FROM cart_item";
        DatabaseConnection factory = new DatabaseConnection();

        try (Connection conn = factory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                allCartItems.add(mapResultSetToCartItem(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allCartItems;
    }

    public CartItem findById(int id) {
        String sql = "SELECT * FROM cart_item WHERE id = ?";
        DatabaseConnection factory = new DatabaseConnection();

        try (Connection conn = factory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToCartItem(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<CartItem> findItemsByCartId(int cartId) {
        List<CartItem> items = new ArrayList<>();
        String sql = "SELECT * FROM cart_item WHERE cart_id = ?";
        DatabaseConnection factory = new DatabaseConnection();

        try (Connection conn = factory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cartId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    items.add(mapResultSetToCartItem(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    public boolean updateCartItem(CartItem cartItem) {
        String sql = "UPDATE cart_item SET quantity = ? WHERE id = ?";
        DatabaseConnection factory = new DatabaseConnection();

        try (Connection conn = factory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cartItem.getQuantity());
            stmt.setInt(2, cartItem.getId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteCartItem(int id) {
        String sql = "DELETE FROM cart_item WHERE id = ?";
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

    private CartItem mapResultSetToCartItem(ResultSet rs) throws SQLException {
        CartDao cartDao = new CartDao();
        ProductDao productDao = new ProductDao();

        Cart cart = cartDao.findById(rs.getInt("cart_id"));
        Product product = productDao.findById(rs.getInt("product_id"));

        return new CartItem(
                rs.getInt("id"),
                cart,
                product,
                rs.getInt("quantity"),
                rs.getDouble("unit_price"),
                utils.SystemClock.now(), // created_at mock
                utils.SystemClock.now()  // updated_at mock
        );
    }
}
