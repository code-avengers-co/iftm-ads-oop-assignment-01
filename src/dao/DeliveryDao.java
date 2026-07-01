package dao;

import model.Delivery;
import model.Order;
import model.enums.DeliveryStatus;
import utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DeliveryDao {

    public boolean saveDelivery(Delivery delivery) {
        String sql = "INSERT INTO delivery (order_id, status, carrier, tracking_code, shipping_date, delivery_date, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        DatabaseConnection factory = new DatabaseConnection();

        try (Connection connection = factory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, delivery.getOrder().getId());
            stmt.setString(2, delivery.getStatus().name());
            stmt.setString(3, delivery.getCarrier());
            stmt.setString(4, delivery.getTrackingCode());
            if (delivery.getShippingDate() != null) {
                stmt.setDate(5, Date.valueOf(delivery.getShippingDate()));
            } else {
                stmt.setNull(5, Types.DATE);
            }
            if (delivery.getDeliveryDate() != null) {
                stmt.setDate(6, Date.valueOf(delivery.getDeliveryDate()));
            } else {
                stmt.setNull(6, Types.DATE);
            }
            stmt.setTimestamp(7, Timestamp.valueOf(delivery.getCreatedAt()));
            stmt.setTimestamp(8, Timestamp.valueOf(delivery.getUpdatedAt()));

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        delivery.setId(rs.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Delivery> getAllDeliveries() {
        List<Delivery> deliveries = new ArrayList<>();
        String sql = "SELECT * FROM delivery";
        DatabaseConnection factory = new DatabaseConnection();

        try (Connection conn = factory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                deliveries.add(mapResultSetToDelivery(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return deliveries;
    }

    public Delivery findById(int id) {
        String sql = "SELECT * FROM delivery WHERE id = ?";
        DatabaseConnection factory = new DatabaseConnection();

        try (Connection conn = factory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToDelivery(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateDelivery(Delivery delivery) {
        String sql = "UPDATE delivery SET status = ?, carrier = ?, tracking_code = ?, shipping_date = ?, delivery_date = ?, updated_at = ? WHERE id = ?";
        DatabaseConnection factory = new DatabaseConnection();

        try (Connection conn = factory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, delivery.getStatus().name());
            stmt.setString(2, delivery.getCarrier());
            stmt.setString(3, delivery.getTrackingCode());
            if (delivery.getShippingDate() != null) {
                stmt.setDate(4, Date.valueOf(delivery.getShippingDate()));
            } else {
                stmt.setNull(4, Types.DATE);
            }
            if (delivery.getDeliveryDate() != null) {
                stmt.setDate(5, Date.valueOf(delivery.getDeliveryDate()));
            } else {
                stmt.setNull(5, Types.DATE);
            }
            stmt.setTimestamp(6, Timestamp.valueOf(delivery.getUpdatedAt()));
            stmt.setInt(7, delivery.getId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteDelivery(int id) {
        String sql = "DELETE FROM delivery WHERE id = ?";
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

    private Delivery mapResultSetToDelivery(ResultSet rs) throws SQLException {
        OrderDao orderDao = new OrderDao();
        Order order = orderDao.findById(rs.getInt("order_id"));

        return new Delivery(
                rs.getInt("id"),
                order,
                DeliveryStatus.valueOf(rs.getString("status")),
                rs.getString("carrier"),
                rs.getString("tracking_code"),
                rs.getDate("shipping_date") != null ? rs.getDate("shipping_date").toLocalDate() : null,
                rs.getDate("delivery_date") != null ? rs.getDate("delivery_date").toLocalDate() : null,
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getTimestamp("updated_at").toLocalDateTime()
        );
    }
}
