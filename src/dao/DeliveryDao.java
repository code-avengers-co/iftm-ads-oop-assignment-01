package dao;

import model.Delivery;
import utils.SystemClock;

public class DeliveryDao {
    private Delivery[] deliveryDb;
    private int deliveryCount;

    public DeliveryDao(int length){
        deliveryDb = new Delivery[length];
        deliveryCount = 0;
    }

    public boolean saveDelivery(Delivery delivery){
        if (deliveryCount >= deliveryDb.length){
            return false; // No more space to save new delivery
        }

        deliveryDb[deliveryCount] = delivery;
        deliveryCount++;

        return true;
    }

    public Delivery[] getAllDeliveries() {
        Delivery[] deliveries = new Delivery[deliveryCount];
        for (int i = 0; i < deliveryCount; i++) {
            if (deliveryDb[i] != null) {
                deliveries[i] = deliveryDb[i];
            }
        }

        return deliveries;
    }

    public Delivery findById(int id){
        for (int i = 0; i < deliveryCount; i++) {
            if (deliveryDb[i].getId() == id) {
                return deliveryDb[i];
            }
        }
        return null; // Delivery not found
    }

    public boolean updateDelivery(Delivery updatedDelivery){
        Delivery delivery = findById(updatedDelivery.getId());
        if (delivery == null) {
            return false; // Delivery not found
        }

        delivery.setDeliveryDate(updatedDelivery.getDeliveryDate());
        delivery.setShippingDate(updatedDelivery.getShippingDate());
        delivery.setStatus(updatedDelivery.getStatus());
        delivery.setCarrier(updatedDelivery.getCarrier());
        delivery.setTrackingCode(updatedDelivery.getTrackingCode());
        delivery.setUpdatedAt(SystemClock.now());

        return true;
    }

    public boolean deleteDelivery(int deliveryId){
        int indexToDelete = -1;
        for (int i = 0; i < deliveryCount; i++) {
            if (deliveryDb[i].getId() == deliveryId){
                indexToDelete = i;
                break;
            }
        }

        if (indexToDelete == -1){
            return false; // Delivery not found
        }

        deliveryDb[indexToDelete] = deliveryDb[deliveryCount - 1];
        deliveryDb[deliveryCount - 1] = null;
        deliveryCount--;

        return true;
    }
}
