package dao;

import model.Product;
import model.StockMovement;
import model.enums.MovementType;
import utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StockMovementDao {

    public boolean saveStockMovement(StockMovement stockMovement) {
        String sql = "INSERT INTO stock_movement (product_id, quantity, movement_type, unit_value, created_at) VALUES (?, ?, ?, ?, ?)";
        DatabaseConnection factory = new DatabaseConnection();

        try (Connection connection = factory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, stockMovement.getProduct().getId());
            stmt.setInt(2, stockMovement.getQuantity());
            stmt.setString(3, stockMovement.getType().name());
            stmt.setDouble(4, stockMovement.getUnitValue());
            stmt.setTimestamp(5, Timestamp.valueOf(stockMovement.getCreatedAt()));

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        stockMovement.setId(rs.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public StockMovement findById(int id) {
        String sql = "SELECT * FROM stock_movement WHERE id = ?";
        DatabaseConnection factory = new DatabaseConnection();

        try (Connection conn = factory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToStockMovement(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<StockMovement> getAllStockMovement() {
        List<StockMovement> stockMovements = new ArrayList<>();
        String sql = "SELECT * FROM stock_movement";
        DatabaseConnection factory = new DatabaseConnection();

        try (Connection conn = factory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                stockMovements.add(mapResultSetToStockMovement(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return stockMovements;
    }

    public List<StockMovement> findMovementsByProductId(int productId) {
        List<StockMovement> stockMovements = new ArrayList<>();
        String sql = "SELECT * FROM stock_movement WHERE product_id = ?";
        DatabaseConnection factory = new DatabaseConnection();

        try (Connection conn = factory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, productId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    stockMovements.add(mapResultSetToStockMovement(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stockMovements;
    }

    public boolean updateStockMovement(StockMovement stockMovement) {
        String sql = "UPDATE stock_movement SET quantity = ?, movement_type = ?, unit_value = ? WHERE id = ?";
        DatabaseConnection factory = new DatabaseConnection();

        try (Connection conn = factory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, stockMovement.getQuantity());
            stmt.setString(2, stockMovement.getType().name());
            stmt.setDouble(3, stockMovement.getUnitValue());
            stmt.setInt(4, stockMovement.getId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteStockMovement(int id) {
        String sql = "DELETE FROM stock_movement WHERE id = ?";
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

    private StockMovement mapResultSetToStockMovement(ResultSet rs) throws SQLException {
        ProductDao productDao = new ProductDao();
        Product product = productDao.findById(rs.getInt("product_id"));

        return new StockMovement(
                rs.getInt("id"),
                product,
                rs.getInt("quantity"),
                MovementType.valueOf(rs.getString("movement_type")),
                rs.getDouble("unit_value"),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getTimestamp("created_at").toLocalDateTime()
        );
    }
}
