package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
    public Connection getConnection() {
        try {
            Properties properties = new Properties();
            properties.setProperty("user", "root");
            properties.setProperty("password", "admin");
            properties.setProperty("useSSL", "false");
            properties.setProperty("useTimezone", "true");
            properties.setProperty("serverTimezone", "America/Sao_Paulo");
            properties.setProperty("allowPublicKeyRetrieval", "true");

            // Database URL
            String stringConnection = "jdbc:mysql://localhost:3306/inventory_db";

            return DriverManager.getConnection(stringConnection, properties);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
