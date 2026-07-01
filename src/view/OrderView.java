package view;

import controller.OrderController;
import model.Order;
import utils.InputUtils;
import utils.PdfReportGenerator;
import utils.UserSession;
import java.util.List;

public class OrderView {
    private OrderController orderController;

    public OrderView(OrderController orderController) {
        this.orderController = orderController;
    }

    public void showMyOrders() {
        System.out.println("\n--- MY ORDERS HISTORY ---");

        int userId = UserSession.getLoggedUser().getId();
        List<Order> myOrders = orderController.getUserOrders(userId);

        if (myOrders.isEmpty()) {
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
        
        System.out.println("\nOptions:");
        System.out.println("1 - Download PDF Report");
        System.out.println("0 - Back");
        
        int option = InputUtils.readInt("Choose an option: ", 0, 1);
        if (option == 1) {
            String fileName = "my_orders_report.pdf";
            PdfReportGenerator.generateOrdersReport(myOrders, fileName);
            System.out.println("PDF generated successfully!");
        }
    }
}
