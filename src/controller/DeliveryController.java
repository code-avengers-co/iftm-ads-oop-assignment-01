package controller;

import dao.DeliveryDao;
import model.Delivery;
import model.enums.DeliveryStatus;
import utils.SystemClock;

import java.util.ArrayList;
import java.util.List;

public class DeliveryController {
    private DeliveryDao deliveryDao;

    public DeliveryController(DeliveryDao deliveryDao) {
        this.deliveryDao = deliveryDao;
    }

    public List<Delivery> getDeliveriesByStatus(DeliveryStatus status) {
        List<Delivery> allDeliveries = deliveryDao.getAllDeliveries();
        List<Delivery> filteredDeliveries = new ArrayList<>();

        for (Delivery d : allDeliveries) {
            if (d.getStatus() == status) {
                filteredDeliveries.add(d);
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
