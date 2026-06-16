package dao;

import model.Product;
import utils.DatabaseConnection;
import utils.SystemClock;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDao {
    public boolean saveProduct(Product product) {
        String sql = "INSERT INTO product (name, description, price, is_active, stock_quantity, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?)";
        DatabaseConnection factory = new DatabaseConnection();

        try (Connection connection = factory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, product.getName());
            stmt.setString(2, product.getDescription());
            stmt.setDouble(3, product.getPrice());
            stmt.setBoolean(4, product.isActive());
            stmt.setInt(5, product.getStockQuantity());
            stmt.setTimestamp(6, Timestamp.valueOf(product.getCreatedAt()));
            stmt.setTimestamp(7, Timestamp.valueOf(product.getUpdatedAt()));

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        product.setId(rs.getInt(1));
                    }
                }

                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<Product> getAllProducts() {
        List<Product> allProducts = new ArrayList<>();
        String sql = "SELECT * FROM product";

        DatabaseConnection factory = new DatabaseConnection();

        try (Connection conn = factory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet resultSet = stmt.executeQuery()) {

            while (resultSet.next()) {
                Product product = new Product(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getDouble("price"),
                        resultSet.getBoolean("is_active"),
                        resultSet.getInt("stock_quantity"),
                        resultSet.getTimestamp("created_at").toLocalDateTime(),
                        resultSet.getTimestamp("updated_at").toLocalDateTime()
                );

                allProducts.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allProducts;
    }

    public List<Product> getActiveProducts() {
        List<Product> activeProducts = new ArrayList<>();
        String sql = "SELECT * FROM product WHERE is_active = true";

        DatabaseConnection factory = new DatabaseConnection();

        try (Connection conn = factory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet resultSet = stmt.executeQuery()) {

            while (resultSet.next()) {
                Product product = new Product(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getDouble("price"),
                        resultSet.getBoolean("is_active"),
                        resultSet.getInt("stock_quantity"),
                        resultSet.getTimestamp("created_at").toLocalDateTime(),
                        resultSet.getTimestamp("updated_at").toLocalDateTime()
                );
                activeProducts.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return activeProducts;
    }

    public Product findById(int id) {
        String sql = "SELECT * FROM product WHERE id = ?";
        DatabaseConnection factory = new DatabaseConnection();

        try (Connection conn = factory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id); // Troca a interrogação pelo ID buscado

            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    return new Product(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("description"),
                            resultSet.getDouble("price"),
                            resultSet.getBoolean("is_active"),
                            resultSet.getInt("stock_quantity"),
                            resultSet.getTimestamp("created_at").toLocalDateTime(),
                            resultSet.getTimestamp("updated_at").toLocalDateTime()
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean updateProduct(Product updatedProduct) {
        String sql = "UPDATE product SET name = ?, description = ?, price = ?, is_active = ?, stock_quantity = ?, updated_at = ? WHERE id = ?";
        DatabaseConnection factory = new DatabaseConnection();

        try (Connection conn = factory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, updatedProduct.getName());
            stmt.setString(2, updatedProduct.getDescription());
            stmt.setDouble(3, updatedProduct.getPrice());
            stmt.setBoolean(4, updatedProduct.isActive());
            stmt.setInt(5, updatedProduct.getStockQuantity());
            stmt.setTimestamp(6, Timestamp.valueOf(utils.SystemClock.now()));
            stmt.setInt(7, updatedProduct.getId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean deleteProduct(int id) {
        String sql = "UPDATE product SET is_active = false, updated_at = ? WHERE id = ?";
        DatabaseConnection factory = new DatabaseConnection();

        try (Connection conn = factory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setTimestamp(1, Timestamp.valueOf(utils.SystemClock.now()));
            stmt.setInt(2, id);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
