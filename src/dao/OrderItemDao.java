package dao;

import model.Order;
import model.OrderItem;
import model.Product;
import utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderItemDao {

    public boolean saveOrderItem(OrderItem orderItem) {
        String sql = "INSERT INTO order_item (order_id, product_id, quantity, unit_price) VALUES (?, ?, ?, ?)";
        DatabaseConnection factory = new DatabaseConnection();

        try (Connection connection = factory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, orderItem.getOrder().getId());
            stmt.setInt(2, orderItem.getProduct().getId());
            stmt.setInt(3, orderItem.getQuantity());
            stmt.setDouble(4, orderItem.getUnitPrice());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        orderItem.setId(rs.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<OrderItem> getOrderItems() {
        List<OrderItem> orderItems = new ArrayList<>();
        String sql = "SELECT * FROM order_item";
        DatabaseConnection factory = new DatabaseConnection();

        try (Connection conn = factory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                orderItems.add(mapResultSetToOrderItem(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orderItems;
    }

    public OrderItem findById(int id) {
        String sql = "SELECT * FROM order_item WHERE id = ?";
        DatabaseConnection factory = new DatabaseConnection();

        try (Connection conn = factory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToOrderItem(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<OrderItem> findItemsByOrderId(int orderId) {
        List<OrderItem> items = new ArrayList<>();
        String sql = "SELECT * FROM order_item WHERE order_id = ?";
        DatabaseConnection factory = new DatabaseConnection();

        try (Connection conn = factory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, orderId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    items.add(mapResultSetToOrderItem(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    public boolean updateOrderItem(OrderItem orderItem) {
        String sql = "UPDATE order_item SET quantity = ?, unit_price = ? WHERE id = ?";
        DatabaseConnection factory = new DatabaseConnection();

        try (Connection conn = factory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, orderItem.getQuantity());
            stmt.setDouble(2, orderItem.getUnitPrice());
            stmt.setInt(3, orderItem.getId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteOrderItem(int id) {
        String sql = "DELETE FROM order_item WHERE id = ?";
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

    private OrderItem mapResultSetToOrderItem(ResultSet rs) throws SQLException {
        OrderDao orderDao = new OrderDao();
        ProductDao productDao = new ProductDao();

        Order order = orderDao.findById(rs.getInt("order_id"));
        Product product = productDao.findById(rs.getInt("product_id"));

        return new OrderItem(
                rs.getInt("id"),
                order,
                product,
                rs.getInt("quantity"),
                rs.getDouble("unit_price"),
                rs.getInt("quantity") * rs.getDouble("unit_price"), // sub_total mock
                utils.SystemClock.now(), // created_at mock
                utils.SystemClock.now()  // updated_at mock
        );
    }
}
