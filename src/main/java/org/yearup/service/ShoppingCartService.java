package org.yearup.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.yearup.models.CartItem;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;
import org.yearup.repository.ShoppingCartRepository;

import java.util.List;

@Service
public class ShoppingCartService
{
    // a shopping cart is built from cart rows plus a product lookup for each row
    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductService productService;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository, ProductService productService)
    {
        this.shoppingCartRepository = shoppingCartRepository;
        this.productService = productService;
    }

    public ShoppingCart getByUserId(int userId)
    {
        // load the user's cart rows, look up each product, and build the ShoppingCart
        ShoppingCart shoppingCart = new ShoppingCart();
        List<CartItem> cartItems = shoppingCartRepository.findByUserId(userId);
        for (CartItem cartItem : cartItems){
            Product product = productService.getById(cartItem.getProductId());

            // Defensive check: if a product was removed, do not break the whole cart response.
            if (product != null)
            {
                ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
                shoppingCartItem.setProduct(product);
                shoppingCartItem.setQuantity(cartItem.getQuantity());
                shoppingCart.add(shoppingCartItem);
            }
        }
        return shoppingCart;
    }
    @Transactional
    public ShoppingCart addProduct(int userId, int productId) {
        CartItem cartItem = shoppingCartRepository.findByUserIdAndProductId(userId, productId);
        if (cartItem == null)
        {
            // this means new product in this user's cart: insert with quantity 1.
            cartItem = new CartItem();
            cartItem.setUserId(userId);
            cartItem.setProductId(productId);
            cartItem.setQuantity(1);
        }
        else {
            // Existing product in this user's cart: increase quantity by 1.
            cartItem.setQuantity(cartItem.getQuantity() + 1);
        }
        shoppingCartRepository.save(cartItem);
        return getByUserId(userId);
    }
    @Transactional
    public ShoppingCart updateProductQuantity(int userId, int productId, int quantity) {
        CartItem cartItem = shoppingCartRepository.findByUserIdAndProductId(userId, productId);
        // The PUT requirement says to update only if the item already exists in the cart.
        if (cartItem != null) {
            if (quantity <= 0) {
                // Quantity 0 or less means the item should not stay in the cart.
                shoppingCartRepository.delete(cartItem);
            }
            else {
                cartItem.setQuantity(quantity);
                shoppingCartRepository.save(cartItem);
            }
        }
        return getByUserId(userId);
    }
}
