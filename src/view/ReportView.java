package view;

import controller.ReportController;
import model.Order;
import model.enums.OrderStatus;
import model.enums.RevenuePeriod;
import utils.InputUtils;
import utils.SystemClock;

import java.time.LocalDate;

public class ReportView {
    private ReportController reportController;

    public ReportView(ReportController reportController) {
        this.reportController = reportController;
    }

    public void showMenu() {
        int option;
        do {
            System.out.println("\n--- ADMIN REPORTS ---");
            System.out.println("1 - Orders by Status");
            System.out.println("2 - Revenue Report");
            System.out.println("0 - Back");

            option = InputUtils.readInt("Choose an option: ", 0, 2);

            switch (option) {
                case 1:
                    handleOrdersByStatus();
                    break;
                case 2:
                    handleRevenueReport();
                    break;
            }
        } while (option != 0);
    }

    private void handleOrdersByStatus() {
        System.out.println("\nSelect the Order Status to filter:");
        System.out.println("1 - Created");
        System.out.println("2 - Paid");
        System.out.println("3 - Preparation");
        System.out.println("4 - Shipped");
        System.out.println("5 - Delivered");
        System.out.println("6 - Cancelled");

        int statusOption = InputUtils.readInt("Choose status: ", 1, 6);

        OrderStatus statusSelected;
        try{
            statusSelected  = getOrderStatusMatched(statusOption);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid option for order status. Returning to menu.");
            return;
        }

        Order[] orders = reportController.getOrdersByStatus(statusSelected);

        System.out.println("\n--- Orders with status: " + statusSelected + " ---");
        if (orders.length == 0) {
            System.out.println("No orders found.");
            return;
        }

        for (Order order : orders) {
            System.out.println("ID: " + order.getId() + " | Date: " + order.getCreatedAt().toLocalDate() +
                    " | Total: R$" + String.format("%.2f", order.getTotalValue()) +
                    " | Client ID: " + order.getUser().getId());
        }
    }

    private void handleRevenueReport() {
        System.out.println("\nSelect the Revenue Period:");
        System.out.println("1 - Daily (Today)");
        System.out.println("2 - Monthly (This Month)");
        System.out.println("3 - Yearly (This Year)");

        int periodOption = InputUtils.readInt("Choose period: ", 1, 3);

        RevenuePeriod periodSelected;
        if (periodOption == 1) {
            periodSelected = RevenuePeriod.DAILY;
        }
        else if (periodOption == 2) {
            periodSelected = RevenuePeriod.MONTHLY;
        }
        else {
            periodSelected = RevenuePeriod.YEARLY;
        }

        LocalDate currentDate = SystemClock.today();

        double revenue = reportController.calculateRevenue(periodSelected, currentDate);
        System.out.println("\nTotal Revenue (" + periodSelected + "): R$" + String.format("%.2f", revenue));
    }

    private OrderStatus getOrderStatusMatched(int option){
        switch (option) {
            case 1:
                return OrderStatus.Created;
            case 2:
                return OrderStatus.Paid;
            case 3:
                return OrderStatus.Preparation;
            case 4:
                return OrderStatus.Shipped;
            case 5:
                return OrderStatus.Delivered;
            case 6:
                return OrderStatus.Canceled;
            default:
                throw new IllegalArgumentException("Invalid option for order status");
        }
    }
}
