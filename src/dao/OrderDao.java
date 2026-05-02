package dao;

import model.Order;

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

    public Order[] getOrders(){
        Order[] orders = new Order[orderCount];
        int currentIndex = 0;

        for (int i = 0; i < orderCount; i++) {
            if (orderDb[i] != null){
                orders[currentIndex]= orderDb[i];
                currentIndex++;
            }
        }

        return orders;
    }
    
    public Order findById(int id){
        for (int i = 0; i < orderCount; i++) {
            if (orderDb[i].getId() == id) {
                return orderDb[i];
            }
        }
        
        return null; // Order not found
    }
}
