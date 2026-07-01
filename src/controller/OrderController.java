package controller;

import dao.OrderDao;
import model.Order;
import java.util.List;

public class OrderController {
    private OrderDao orderDao;

    public OrderController(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public List<Order> getUserOrders(int userId) {
        return orderDao.findOrdersByUserId(userId);
    }
}
