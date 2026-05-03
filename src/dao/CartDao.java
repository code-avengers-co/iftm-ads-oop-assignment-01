package dao;

import model.Cart;
import utils.SystemClock;

import java.time.LocalDateTime;

public class CartDao {
    private Cart[] cartDb;
    private int cartCount;

    public CartDao(int length){
        cartDb = new Cart[length];
        cartCount = 0;
    }

    public boolean saveCart(Cart cart){
        if (cartCount >= cartDb.length) {
            return false; // No more space to save new cart
        }

        cartDb[cartCount] = cart;
        cartCount++;

        return true;
    }

    public Cart[] getCarts(){
        Cart[] carts = new Cart[cartCount];
        int currentIndex = 0;

        for (int i = 0; i < cartCount; i++) {
            if (cartDb[i] != null){
                carts[currentIndex]= cartDb[i];
                currentIndex++;
            }
        }

        return carts;
    }

    public Cart findById(int id){
        for (int i = 0; i < cartCount; i++) {
            if (cartDb[i].getId() == id) {
                return cartDb[i];
            }
        }

        return null; // Cart not found
    }

    public Cart findOpenCartByUser(int userId){
        for (int i = 0; i < cartCount; i++) {
            Cart currentCart = cartDb[i];
            if (currentCart != null &&
                    currentCart.getUser().getId() == userId &&
                    currentCart.getStatus() == model.enums.CartStatus.Open) {

                return currentCart;
            }
        }

        return null; // Cart not Found
    }

    public boolean updateCart(Cart updatedCart) {
        Cart cart = findById(updatedCart.getId());
        if (cart == null) {
            return false; // Cart not found
        }

        cart.setStatus(updatedCart.getStatus());
        cart.setUpdatedAt(SystemClock.now());

        return true;
    }
}
