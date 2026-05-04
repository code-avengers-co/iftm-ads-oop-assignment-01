package dao;

import model.CartItem;
import utils.SystemClock;

import java.time.LocalDateTime;

public class CartItemDao {
    private CartItem[] cartItemDb;
    private int cartItemCount;

    public CartItemDao(int length){
        cartItemDb = new CartItem[length];
        cartItemCount = 0;
    }

    public boolean saveCartItem(CartItem cartItem){
        if (cartItemCount >= cartItemDb.length) {
            return false; // No more space to save new cart item
        }

        cartItemDb[cartItemCount] = cartItem;
        cartItemCount++;

        return true;
    }

    public CartItem[] getAllCartItems(){
        CartItem[] allCartItems = new CartItem[cartItemCount];

        int currentIndex = 0;
        for (int i = 0; i < cartItemCount; i++) {
            allCartItems[currentIndex] = cartItemDb[i];
            currentIndex++;
        }

        return allCartItems;
    }

    public CartItem findById(int id){
        for (int i = 0; i < cartItemCount; i++) {
            if (cartItemDb[i].getId() == id) {
                return cartItemDb[i];
            }
        }

        return null; // CartItem not found
    }

    public CartItem[] findItemsByCartId(int cartId){
        // 1. Create an Array with max length
        CartItem[] tempItems = new CartItem[cartItemCount];
        int count = 0;

        // 2. Get the items
        for (int i = 0; i < cartItemCount; i++) {
            if (cartItemDb[i].getCart().getId() == cartId) {
                tempItems[count] = cartItemDb[i];
                count++;
            }
        }

        // 3. Create an array with the exact length and copy the items
        CartItem[] exactItems = new CartItem[count];
        for (int i = 0; i < count; i++) {
            exactItems[i] = tempItems[i];
        }

        return exactItems;
    }

    public boolean updateCartItem(CartItem updatedCartItem) {
        CartItem cartItem = findById(updatedCartItem.getId());
        if (cartItem == null) {
            return false; // CartItem not found
        }

        cartItem.setQuantity(updatedCartItem.getQuantity());
        cartItem.setUpdatedAt(SystemClock.now());

        return true;
    }

    public boolean deleteCartItem(int id) {
        int indexToDeleted = -1;
        for (int i = 0; i < cartItemCount; i++) {
            if(cartItemDb[i].getId() == id){
                indexToDeleted = i;
                break;
            }
        }

        if (indexToDeleted == -1){
            return false; // CartItem not found
        }

        // Shift the last cart item to the deleted index and set the last index to null
        cartItemDb[indexToDeleted] = cartItemDb[cartItemCount - 1];
        cartItemDb[cartItemCount - 1] = null;
        cartItemCount--;

        return true;
    }
}
