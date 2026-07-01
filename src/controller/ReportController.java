package controller;

import dao.OrderDao;
import model.Order;
import model.enums.OrderStatus;
import model.enums.RevenuePeriod;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReportController {
    private OrderDao orderDao;

    public ReportController(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public List<Order> getOrdersByStatus(OrderStatus status) {
        List<Order> allOrders = orderDao.getAllOrders();
        List<Order> exactOrders = new ArrayList<>();

        for (Order order : allOrders) {
            if (order.getStatus() == status) {
                exactOrders.add(order);
            }
        }

        return exactOrders;
    }

    public double calculateRevenue(RevenuePeriod period, LocalDate referenceDate) {
        List<Order> allOrders = orderDao.getAllOrders();
        double totalRevenue = 0;

        for (Order order : allOrders) {
            if (order.getStatus() != OrderStatus.Canceled) {
                LocalDate orderDate = order.getCreatedAt().toLocalDate();

                boolean match = false;
                switch (period) {
                    case DAILY:
                        match = orderDate.isEqual(referenceDate);
                        break;
                    case MONTHLY:
                        match = (orderDate.getMonth() == referenceDate.getMonth() &&
                                orderDate.getYear() == referenceDate.getYear());
                        break;
                    case YEARLY:
                        match = (orderDate.getYear() == referenceDate.getYear());
                        break;
                }

                if (match) {
                    totalRevenue += order.getTotalValue();
                }
            }
        }
        return totalRevenue;
    }
}
