package utils;

public class CreationIdUtils {
    private static int nextProductId = 1;
    private static int nextPersonId = 1;
    private static int nextUserId = 1;
    private static int nextCartId = 1;
    private static int nextCartItemId = 1;
    private static int nextOrderId = 1;
    private static int nextOrderItemId = 1;
    private static int nextStockMovementId = 1;
    private static int nextCouponId = 1;
    private static int nextDeliveryId = 1;

    public static int generateProductId(){
        int idToReturn = nextProductId;
        nextProductId++;
        return idToReturn;
    }

    public static int generatePersonId(){
        int idToReturn = nextPersonId;
        nextPersonId++;
        return idToReturn;
    }

    public static int generateUserId(){
        int idToReturn = nextUserId;
        nextUserId++;
        return idToReturn;
    }

    public static int generateCartId(){
        int idToReturn = nextCartId;
        nextCartId++;
        return idToReturn;
    }

    public static int generateCartItemId() {
        int idToReturn = nextCartItemId;
        nextCartItemId++;
        return idToReturn;
    }

    public static int generateOrderId() {
        int idToReturn = nextOrderId;
        nextOrderId++;
        return idToReturn;
    }

    public static int generateOrderItemId() {
        int idToReturn = nextOrderItemId;
        nextOrderItemId++;
        return idToReturn;
    }

    public static int generateStockMovementId() {
        int idToReturn = nextStockMovementId;
        nextStockMovementId++;
        return idToReturn;
    }

    public static int generateCouponId() {
        int idToReturn = nextCouponId;
        nextCouponId++;
        return idToReturn;
    }

    public static int generateDeliveryId() {
        int idToReturn = nextDeliveryId;
        nextDeliveryId++;
        return idToReturn;
    }
}
