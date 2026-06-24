package org.yearup.models;

import jakarta.persistence.*;
// Represents one row in the shopping_cart table.
// Each row tracks which user added which product and how many they want.
@Entity
@Table(name = "shopping_cart")
public class CartItem
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private int cartItemId;
    // The user who owns this cart item
    @Column(name = "user_id")
    private int userId;
    // The product that was added to the cart
    @Column(name = "product_id")
    private int productId;
    // Defaults to 1 when first added — increments on repeated adds
    @Column(name = "quantity")
    private int quantity = 1;

    public int getCartItemId()
    {
        return cartItemId;
    }

    public void setCartItemId(int cartItemId)
    {
        this.cartItemId = cartItemId;
    }

    public int getUserId()
    {
        return userId;
    }

    public void setUserId(int userId)
    {
        this.userId = userId;
    }

    public int getProductId()
    {
        return productId;
    }

    public void setProductId(int productId)
    {
        this.productId = productId;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }
}
