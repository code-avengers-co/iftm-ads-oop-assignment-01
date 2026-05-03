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
}
