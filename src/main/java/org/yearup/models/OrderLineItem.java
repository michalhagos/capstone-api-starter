package org.yearup.models;

import jakarta.persistence.*;
// Represents one product line in an order.
@Entity
@Table(name = "order_line_items")
public class OrderLineItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_line_item_id")
    private int orderLineItemId;
    // The order this line item belongs to
    @Column(name = "order_id")
    private int orderId;
    // The product that was purchased
    @Column(name = "product_id")
    private int productId;
    // Price at the time of purchase, stored here so it doesn't change if the product price is updated later
    @Column(name = "sales_price")
    private double salesPrice;

    @Column(name = "quantity")
    private int quantity;
    // Discount applied to this line item at checkout (0 if none)
    @Column(name = "discount")
    private double discount;

    public int getOrderLineItemId() {
        return orderLineItemId;
    }

    public void setOrderLineItemId(int orderLineItemId) {
        this.orderLineItemId = orderLineItemId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public double getSalesPrice() {
        return salesPrice;
    }

    public void setSalesPrice(double salesPrice) {
        this.salesPrice = salesPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

}
