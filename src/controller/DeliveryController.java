package controller;

import dao.DeliveryDao;
import model.Delivery;
import model.enums.DeliveryStatus;
import utils.SystemClock;

public class DeliveryController {
    private DeliveryDao deliveryDao;

    public DeliveryController(DeliveryDao deliveryDao) {
        this.deliveryDao = deliveryDao;
    }

    public Delivery[] getDeliveriesByStatus(DeliveryStatus status) {
        Delivery[] allDeliveries = deliveryDao.getAllDeliveries();
        int count = 0;

        for (Delivery delivery : allDeliveries) {
            if (delivery.getStatus() == status) count++;
        }

        Delivery[] filteredDeliveries = new Delivery[count];
        int index = 0;
        for (Delivery d : allDeliveries) {
            if (d.getStatus() == status) {
                filteredDeliveries[index] = d;
                index++;
            }
        }

        return filteredDeliveries;
    }

    public boolean updateCarrierAndTracking(int deliveryId, String carrier, String trackingCode) {
        Delivery delivery = deliveryDao.findById(deliveryId);

        if (delivery == null) {
            return false;
        }

        delivery.setCarrier(carrier);
        delivery.setTrackingCode(trackingCode);
        delivery.setUpdatedAt(SystemClock.now());

        return deliveryDao.updateDelivery(delivery);
    }
}
