package controller;

import dao.DeliveryDao;
import dao.OrderDao;
import model.Delivery;
import model.Order;
import model.enums.DeliveryStatus;
import utils.SystemClock;

import java.util.ArrayList;
import java.util.List;

public class DeliveryController {
    private DeliveryDao deliveryDao;
    private OrderDao orderDao;

    public DeliveryController(DeliveryDao deliveryDao, OrderDao orderDao) {
        this.deliveryDao = deliveryDao;
        this.orderDao = orderDao;
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
        delivery.setStatus(DeliveryStatus.Shipped);
        delivery.setShippingDate(SystemClock.today());
        delivery.setUpdatedAt(SystemClock.now());

        if (deliveryDao.updateDelivery(delivery)) {
            Order order = delivery.getOrder();
            order.setStatus(model.enums.OrderStatus.Shipped);
            order.setUpdatedAt(SystemClock.now());
            return orderDao.updateOrder(order);
        }
        
        return false;
    }
}
