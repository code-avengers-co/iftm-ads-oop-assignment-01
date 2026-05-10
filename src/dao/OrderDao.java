package dao;

import model.Order;
import utils.SystemClock;

public class OrderDao {
    private Order[] orderDb;
    private int orderCount;

    public OrderDao(int length){
        orderDb = new Order[length];
        orderCount = 0;
    }

    public boolean saveOrder(Order order){
        if (orderCount >= orderDb.length) {
            return false; // No more space to save new order
        }

        orderDb[orderCount] = order;
        orderCount++;

        return true;
    }

    public Order[] getAllOrders() {
        Order[] exactOrders = new Order[orderCount];
        for (int i = 0; i < orderCount; i++) {
            exactOrders[i] = orderDb[i];
        }
        return exactOrders;
    }
    
    public Order findById(int id){
        for (int i = 0; i < orderCount; i++) {
            if (orderDb[i].getId() == id) {
                return orderDb[i];
            }
        }

        return null; // Order not found
    }

    public Order[] findOrdersByUserId(int userId) {
        Order[] tempOrders = new Order[orderCount];
        int count = 0;

        for (int i = 0; i < orderCount; i++) {
            if (orderDb[i].getUser().getId() == userId) {
                tempOrders[count] = orderDb[i];
                count++;
            }
        }

        Order[] exactOrders = new Order[count];
        for (int i = 0; i < count; i++) {
            exactOrders[i] = tempOrders[i];
        }

        return exactOrders;
    }

    public boolean updateOrder(Order updatedOrder){
        Order order = findById(updatedOrder.getId());
        if (order == null) {
            return false; // Order not found
        }

        order.setStatus(updatedOrder.getStatus());
        order.setCoupon(updatedOrder.getCoupon());
        order.setTotalValue(updatedOrder.getTotalValue());
        order.setPaymentMethod(updatedOrder.getPaymentMethod());
        order.setUpdatedAt(SystemClock.now());

        return true;
    }

    public boolean deleteOrder(int id){
        int indexToDelete = -1;
        for (int i = 0; i < orderCount; i++) {
            if (orderDb[i].getId() == id){
                indexToDelete = i;
                break;
            }
        }

        if (indexToDelete == -1){
            return false; // Order not found
        }

        orderDb[indexToDelete] = orderDb[orderCount - 1];
        orderDb[orderCount - 1] = null;
        orderCount--;

        return true;
    }
}