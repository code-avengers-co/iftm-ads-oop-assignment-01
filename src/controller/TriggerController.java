package controller;

import dao.CartDao;
import dao.CartItemDao;
import dao.DeliveryDao;
import dao.OrderDao;
import model.Cart;
import model.CartItem;
import model.Delivery;
import model.Order;
import model.enums.CartStatus;
import model.enums.DeliveryStatus;
import model.enums.OrderStatus;
import utils.SystemClock;

import java.time.LocalDateTime;

public class TriggerController {
    private CartDao cartDao;
    private CartItemDao cartItemDao;
    private OrderDao orderDao;
    private DeliveryDao deliveryDao;

    public TriggerController(CartDao cartDao, CartItemDao cartItemDao, OrderDao orderDao, DeliveryDao deliveryDao) {
        this.cartDao = cartDao;
        this.cartItemDao = cartItemDao;
        this.orderDao = orderDao;
        this.deliveryDao = deliveryDao;
    }

    public void runTriggers() {
        LocalDateTime now = SystemClock.now();
        processExpiredCarts(now);
        processOrderStatusUpdates(now);
    }

    private void processExpiredCarts(LocalDateTime now) {
        Cart[] allCarts = cartDao.getAllCarts();

        for (Cart cart : allCarts) {
            // 1. Cart Open + 24h -> Expired
            if (cart.getStatus() == CartStatus.Open && !cart.getCreatedAt().plusHours(24).isAfter(now)) {
                // 2. Change to expired
                cart.setStatus(CartStatus.Expired);
                cart.setUpdatedAt(now);
                cartDao.updateCart(cart);

                // 3. Remove items from cart (Clean Cart):
                CartItem[] items = cartItemDao.findItemsByCartId(cart.getId());
                for (CartItem item : items) {
                    cartItemDao.deleteCartItem(item.getId());
                }
            }
        }
    }

    private void processOrderStatusUpdates(LocalDateTime now) {
        Order[] allOrders = orderDao.getAllOrders();
        Delivery[] allDeliveries = deliveryDao.getAllDeliveries();

        for (Order order : allOrders) {
            // 1. + 48h after order creation -> Delivered
            if (order.getStatus() != OrderStatus.Delivered &&
                order.getStatus() != OrderStatus.Canceled &&
                !order.getCreatedAt().plusHours(48).isAfter(now)){

                order.setStatus(OrderStatus.Delivered);
                order.setUpdatedAt(now);
                orderDao.updateOrder(order);

                Delivery delivery = findDeliveryByOrderId(allDeliveries, order.getId());
                if (delivery != null) {
                    delivery.setStatus(DeliveryStatus.Delivered);
                    delivery.setDeliveryDate(SystemClock.today());
                    deliveryDao.updateDelivery(delivery);
                }

                continue; // Skip other status updates if order is already delivered
            }

            // 2. + 24h after order creation -> Shipped (if Paid or Preparation)
            if (order.getStatus() == OrderStatus.Paid || order.getStatus() == OrderStatus.Preparation) {
                if (!order.getCreatedAt().plusHours(24).isAfter(now)) {
                    order.setStatus(OrderStatus.Shipped);
                    order.setUpdatedAt(now);
                    orderDao.updateOrder(order);

                    Delivery delivery = findDeliveryByOrderId(allDeliveries, order.getId());
                    if (delivery != null) {
                        delivery.setStatus(DeliveryStatus.Shipped);
                        delivery.setShippingDate(SystemClock.today());
                        deliveryDao.updateDelivery(delivery);
                    }
                }
            }
        }
    }

    private Delivery findDeliveryByOrderId(Delivery[] deliveries, int orderId) {
        for (Delivery d : deliveries) {
            if (d.getOrder().getId() == orderId) {
                return d;
            }
        }
        return null;
    }
}
