package dao;

import model.OrderItem;
import utils.SystemClock;

import java.time.LocalDateTime;

public class OrderItemDao {
    private OrderItem[] orderItemDb;
    private int orderItemCount;

    public OrderItemDao(int length){
        orderItemDb = new OrderItem[length];
        orderItemCount = 0;
    }

    public boolean saveOrderItem(OrderItem orderItem){
        if (orderItemCount >= orderItemDb.length) {
            return false; // No more space to save new order item
        }

        orderItemDb[orderItemCount] = orderItem;
        orderItemCount++;

        return true;
    }

    public OrderItem[] getOrderItems(){
        OrderItem[] orderItems = new OrderItem[orderItemCount];
        int currentIndex = 0;

        for (int i = 0; i < orderItemCount; i++) {
            if (orderItemDb[i] != null){
                orderItems[currentIndex]= orderItemDb[i];
                currentIndex++;
            }
        }

        return orderItems;
    }

    public OrderItem findById(int id){
        for (int i = 0; i < orderItemCount; i++) {
            if (orderItemDb[i].getId() == id) {
                return orderItemDb[i];
            }
        }

        return null; // Order item not found
    }

    public OrderItem[] findItemsByOrderId(int orderId){
        OrderItem[] tempItems = new OrderItem[orderItemCount];
        int count = 0;

        for (int i = 0; i < orderItemCount; i++) {
            if (orderItemDb[i].getOrder().getId() == orderId) {
                tempItems[count] = orderItemDb[i];
                count++;
            }
        }

        OrderItem[] exactItem = new OrderItem[count];
        for (int i = 0; i < count; i++) {
            exactItem[i] = tempItems[i];
        }

        return exactItem;
    }

    public boolean updateOrderItem(OrderItem updatedOrderItem){
        OrderItem orderItem = findById(updatedOrderItem.getId());
        if (orderItem == null) {
            return false; // Order item not found
        }

        orderItem.setQuantity(updatedOrderItem.getQuantity());
        orderItem.setUnitPrice(updatedOrderItem.getUnitPrice());
        orderItem.setUpdatedAt(SystemClock.now());

        return true;
    }

    public boolean deleteOrderItem(int id){
        int indexToDelete = -1;
        for (int i = 0; i < orderItemCount; i++) {
            if (orderItemDb[i].getId() == id){
                indexToDelete = i;
                break;
            }
        }

        if (indexToDelete == -1){
            return false; // Order item not found
        }

        orderItemDb[indexToDelete] = orderItemDb[orderItemCount - 1];
        orderItemDb[orderItemCount - 1] = null;
        orderItemCount--;

        return true;
    }
}
