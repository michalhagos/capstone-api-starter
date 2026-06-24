package org.yearup.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
// Represents one product entry inside a user's shopping cart.
public class ShoppingCartItem
{
    // Full product object so the cart response includes all product details
    private Product product = null;
    // Defaults to 1 when first added to the cart
    private int quantity = 1;
    // Applied discount rate
    private double discountPercent = 0;

    public Product getProduct()
    {
        return product;
    }

    public void setProduct(Product product)
    {
        this.product = product;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }

    public double getDiscountPercent()
    {
        return discountPercent;
    }

    public void setDiscountPercent(double discountPercent)
    {
        this.discountPercent = discountPercent;
    }

    // Excluded from JSON response productId is already inside the product object,
    // so we don't need to repeat it at the cart item level
    @JsonIgnore
    public int getProductId()
    {
        return this.product.getProductId();
    }
    // Calculates the total cost for this line: (price × quantity) minus any discount
    public double getLineTotal()
    {
        double basePrice = product.getPrice();
        double subTotal = basePrice * this.quantity;
        double discountAmount = subTotal * discountPercent;

        return subTotal - discountAmount;
    }
}
