package dao;

import model.Delivery;

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
}
