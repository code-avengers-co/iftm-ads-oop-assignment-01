package controller;

import dao.CartDao;
import dao.CartItemDao;
import dao.ProductDao;
import model.Cart;
import model.CartItem;
import model.Product;
import model.User;
import utils.UserSession;

import java.util.ArrayList;
import java.util.List;

public class CartController {
    private CartDao cartDao;
    private CartItemDao cartItemDao;
    private ProductDao productDao;

    public CartController(CartDao cartDao, CartItemDao cartItemDao, ProductDao productDao) {
        this.cartDao = cartDao;
        this.cartItemDao = cartItemDao;
        this.productDao = productDao;
    }

    public boolean addProductToCart(int productId, int quantity) {
        if (!UserSession.isLoggedIn()){
            return false; // User is not logged in, cannot add product to cart
        }

        User user = UserSession.getLoggedUser();
        int userId = user.getId();

        // 1. Find the open cart for the user
        Cart cart = cartDao.findOpenCartByUser(userId);
        if (cart == null) {
            // 1.1 If it doesn't exist create one and save it.
            cart = new Cart(user);
            boolean cartSaved = cartDao.saveCart(cart);
            if (!cartSaved){
                return false; // Failed to save in DB
            }
        }

        // 2. Find the product by id
        Product product = productDao.findById(productId);
        if (product == null || !product.isActive()) {
            return false; // Product not found or not active, cannot add to cart
        }

        // 3. Iterate over items of cart and try to find duplicates
        List<CartItem> cartItems = cartItemDao.findItemsByCartId(cart.getId());

        for (CartItem cartItem : cartItems){
            // 3.1 Find a duplicate
            if (cartItem.getProduct().getId() == productId){
                // 3.2 If found, update the quantity and save it.
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
                return cartItemDao.updateCartItem(cartItem);
            }
        }

        // 4. If no duplicate found, create a new cart item and save it.
        CartItem newCartItem = new CartItem(
                cart,
                product,
                quantity,
                product.getPrice()
        );

        return cartItemDao.saveCartItem(newCartItem);
    }

    public List<CartItem> getLoggedUserCartItems() {
        if (!UserSession.isLoggedIn()) {
            return new ArrayList<>(); // User is not logged in, return empty array
        }

        int userId = UserSession.getLoggedUser().getId();
        Cart openCart = cartDao.findOpenCartByUser(userId);

        // If there is no open cart for the user, return an empty array
        if (openCart == null) {
            return new ArrayList<>();
        }

        // Return the items of the open cart
        return cartItemDao.findItemsByCartId(openCart.getId());
    }

    public double getLoggedUserCartTotal() {
        List<CartItem> items = getLoggedUserCartItems();
        double total = 0;

        for (CartItem item : items) {
            total += item.getQuantity() * item.getUnitPrice();
        }

        return total;
    }

    public boolean removeProductFromCart(int productId) {
        if (!UserSession.isLoggedIn()) {
            return false;
        }

        int userId = UserSession.getLoggedUser().getId();
        Cart cart = cartDao.findOpenCartByUser(userId);

        if (cart == null) {
            return false;
        }

        List<CartItem> items = cartItemDao.findItemsByCartId(cart.getId());

        for (CartItem item : items) {
            if (item.getProduct().getId() == productId) {
                return cartItemDao.deleteCartItem(item.getId());
            }
        }

        return false; // Product not found in cart
    }
}
