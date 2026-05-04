package controller;

import dao.OrderDao;
import model.Order;
import model.enums.OrderStatus;
import model.enums.RevenuePeriod;

import java.time.LocalDate;

public class ReportController {
    private OrderDao orderDao;

    public ReportController(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public Order[] getOrdersByStatus(OrderStatus status) {
        Order[] allOrders = orderDao.getAllOrders();
        Order[] tempOrders = new Order[allOrders.length];
        int count = 0;

        for (Order order : allOrders) {
            if (order.getStatus() == status) {
                tempOrders[count] = order;
                count++;
            }
        }

        Order[] exactOrders = new Order[count];
        for (int i = 0; i < count; i++) {
            exactOrders[i] = tempOrders[i];
        }

        return exactOrders;
    }

    public double calculateRevenue(RevenuePeriod period, LocalDate referenceDate) {
        Order[] allOrders = orderDao.getAllOrders();
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
