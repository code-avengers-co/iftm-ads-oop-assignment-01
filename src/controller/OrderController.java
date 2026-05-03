package controller;

import dao.OrderDao;
import model.Order;

public class OrderController {
    private OrderDao orderDao;

    public OrderController(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public Order[] getUserOrders(int userId) {
        return orderDao.findOrdersByUserId(userId);
    }
}
