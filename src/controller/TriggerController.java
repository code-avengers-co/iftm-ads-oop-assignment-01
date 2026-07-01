package controller;

import utils.DatabaseConnection;
import utils.SystemClock;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class TriggerController {

    public TriggerController() {
    }

    public void runTriggers() {
        LocalDateTime now = SystemClock.now();
        processExpiredCarts(now);
        processOrderStatusUpdates(now);
    }

    private void processExpiredCarts(LocalDateTime now) {
        DatabaseConnection factory = new DatabaseConnection();
        LocalDateTime threshold = now.minusHours(24);

        String deleteItemsSql = "DELETE FROM cart_item WHERE cart_id IN (SELECT id FROM cart WHERE status = 'Open' AND created_at <= ?)";
        String updateCartSql = "UPDATE cart SET status = 'Expired', updated_at = ? WHERE status = 'Open' AND created_at <= ?";

        try (Connection conn = factory.getConnection()) {
            conn.setAutoCommit(false); // Transactions for consistency

            try (PreparedStatement deleteItemsStmt = conn.prepareStatement(deleteItemsSql);
                 PreparedStatement updateCartStmt = conn.prepareStatement(updateCartSql)) {

                // Delete items
                deleteItemsStmt.setTimestamp(1, Timestamp.valueOf(threshold));
                deleteItemsStmt.executeUpdate();

                // Update carts
                updateCartStmt.setTimestamp(1, Timestamp.valueOf(now));
                updateCartStmt.setTimestamp(2, Timestamp.valueOf(threshold));
                updateCartStmt.executeUpdate();

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void processOrderStatusUpdates(LocalDateTime now) {
        DatabaseConnection factory = new DatabaseConnection();

        LocalDateTime deliveredThreshold = now.minusHours(48);
        LocalDateTime shippedThreshold = now.minusHours(24);
        Date today = Date.valueOf(SystemClock.today());

        String updateDeliveryToDeliveredSql = "UPDATE delivery d INNER JOIN orders o ON d.order_id = o.id SET d.status = 'Delivered', d.delivery_date = ?, d.updated_at = ? WHERE o.status NOT IN ('Delivered', 'Canceled') AND o.created_at <= ?";
        String updateOrderToDeliveredSql = "UPDATE orders SET status = 'Delivered', updated_at = ? WHERE status NOT IN ('Delivered', 'Canceled') AND created_at <= ?";

        String updateDeliveryToShippedSql = "UPDATE delivery d INNER JOIN orders o ON d.order_id = o.id SET d.status = 'Shipped', d.shipping_date = ?, d.updated_at = ? WHERE o.status IN ('Paid', 'Preparation') AND o.created_at <= ?";
        String updateOrderToShippedSql = "UPDATE orders SET status = 'Shipped', updated_at = ? WHERE status IN ('Paid', 'Preparation') AND created_at <= ?";

        try (Connection conn = factory.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement updateDelvDeliveredStmt = conn.prepareStatement(updateDeliveryToDeliveredSql);
                 PreparedStatement updateOrderDeliveredStmt = conn.prepareStatement(updateOrderToDeliveredSql);
                 PreparedStatement updateDelvShippedStmt = conn.prepareStatement(updateDeliveryToShippedSql);
                 PreparedStatement updateOrderShippedStmt = conn.prepareStatement(updateOrderToShippedSql)) {

                // 1. Process Deliveries -> Delivered
                updateDelvDeliveredStmt.setDate(1, today);
                updateDelvDeliveredStmt.setTimestamp(2, Timestamp.valueOf(now));
                updateDelvDeliveredStmt.setTimestamp(3, Timestamp.valueOf(deliveredThreshold));
                updateDelvDeliveredStmt.executeUpdate();

                // 2. Process Orders -> Delivered
                updateOrderDeliveredStmt.setTimestamp(1, Timestamp.valueOf(now));
                updateOrderDeliveredStmt.setTimestamp(2, Timestamp.valueOf(deliveredThreshold));
                updateOrderDeliveredStmt.executeUpdate();

                // 3. Process Deliveries -> Shipped
                updateDelvShippedStmt.setDate(1, today);
                updateDelvShippedStmt.setTimestamp(2, Timestamp.valueOf(now));
                updateDelvShippedStmt.setTimestamp(3, Timestamp.valueOf(shippedThreshold));
                updateDelvShippedStmt.executeUpdate();

                // 4. Process Orders -> Shipped
                updateOrderShippedStmt.setTimestamp(1, Timestamp.valueOf(now));
                updateOrderShippedStmt.setTimestamp(2, Timestamp.valueOf(shippedThreshold));
                updateOrderShippedStmt.executeUpdate();

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
