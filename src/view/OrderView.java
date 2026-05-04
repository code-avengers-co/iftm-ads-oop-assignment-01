package view;

import controller.OrderController;
import model.Order;
import utils.UserSession;

public class OrderView {
    private OrderController orderController;

    public OrderView(OrderController orderController) {
        this.orderController = orderController;
    }

    public void showMyOrders() {
        System.out.println("\n--- MY ORDERS HISTORY ---");

        int userId = UserSession.getLoggedUser().getId();
        Order[] myOrders = orderController.getUserOrders(userId);

        if (myOrders.length == 0) {
            System.out.println("You haven't placed any orders yet.");
            return;
        }

        for (Order order : myOrders) {
            System.out.println("Order ID: " + order.getId() +
                    " | Date: " + order.getCreatedAt().toLocalDate() +
                    " | Status: " + order.getStatus() +
                    " | Total: R$" + String.format("%.2f", order.getTotalValue()) +
                    " | Payment: " + order.getPaymentMethod());
        }
        System.out.println("-------------------------");
    }
}
