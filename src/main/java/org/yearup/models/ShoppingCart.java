package org.yearup.models;

import java.util.HashMap;
import java.util.Map;
// Represents a user's full shopping cart.
// Items are stored in a map keyed by productId so lookups are fast
public class ShoppingCart {
    // Key is productId, value is the full cart item with product details and quantity
    private Map<Integer, ShoppingCartItem> items = new HashMap<>();

    public Map<Integer, ShoppingCartItem> getItems()
    {
        return items;
    }

    public void setItems(Map<Integer, ShoppingCartItem> items)
    {
        this.items = items;
    }
    // Returns true if the product is already in the cart
    public boolean contains(int productId)
    {
        return items.containsKey(productId);
    }

    // Adds a new item to the cart, or replaces the existing entry for that product
    public void add(ShoppingCartItem item)
    {
        items.put(item.getProductId(), item);
    }

    public ShoppingCartItem get(int productId)
    {
        return items.get(productId);
    }

    public double getTotal()
    {
        double total = items.values()
                            .stream()
                            .mapToDouble(i -> i.getLineTotal())
                            .sum();

        return total;
    }

}
