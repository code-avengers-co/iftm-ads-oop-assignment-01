package view;

import controller.DeliveryController;
import model.Delivery;
import model.enums.DeliveryStatus;
import utils.InputUtils;
import java.util.List;

public class DeliveryView{
    private DeliveryController deliveryController;

    public DeliveryView(DeliveryController deliveryController) {
        this.deliveryController = deliveryController;
    }

    public void showMenu() {
        int option;
        do {
            System.out.println("\n--- MANAGE DELIVERIES ---");
            System.out.println("1 - View Pending Deliveries (PREPARING)");
            System.out.println("2 - Dispatch Delivery (Update Carrier & Tracking Code)");
            System.out.println("0 - Back");

            option = InputUtils.readInt("Choose an option: ", 0, 2);

            switch (option) {
                case 1:
                    handleViewPendingDeliveries();
                    break;
                case 2:
                    handleUpdateCarrier();
                    break;
            }
        } while (option != 0);
    }

    private void handleViewPendingDeliveries() {
        List<Delivery> pendingDeliveries = deliveryController.getDeliveriesByStatus(DeliveryStatus.Preparing);

        System.out.println("\n--- PENDING DELIVERIES ---");
        if (pendingDeliveries.isEmpty()) {
            System.out.println("No pending deliveries at the moment.");
            return;
        }

        for (Delivery d : pendingDeliveries) {
            System.out.println("Delivery ID: " + d.getId() +
                    " | Order ID: " + d.getOrder().getId() +
                    " | Status: " + d.getStatus() +
                    " | Date: " + d.getCreatedAt().toLocalDate());
        }
    }

    private void handleUpdateCarrier() {
        handleViewPendingDeliveries();

        int deliveryId = InputUtils.readInt("\nEnter Delivery ID to dispatch (-1 to cancel): ", -1, Integer.MAX_VALUE);
        if (deliveryId == -1) return;

        String carrier = InputUtils.readString("Enter Carrier Name (e.g., Correios, Sedex): ");
        String trackingCode = InputUtils.readString("Enter Tracking Code: ");

        boolean success = deliveryController.updateCarrierAndTracking(deliveryId, carrier, trackingCode);

        if (success) {
            System.out.println("Delivery updated successfully! Package is ready for transit.");
        } else {
            System.out.println("Error: Delivery ID not found.");
        }
    }
}
