package org.yearup.service;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yearup.models.Order;
import org.yearup.models.OrderLineItem;
import org.yearup.models.Profile;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;
import org.yearup.repository.OrderLineItemRepository;
import org.yearup.repository.OrderRepository;
import java.time.LocalDateTime;
import java.util.Map;

@Service

public class OrderService
{
    private final OrderRepository orderRepository;
    private final OrderLineItemRepository orderLineItemRepository;
    private final ProfileService profileService;
    private final ShoppingCartService shoppingCartService;

    public OrderService(OrderRepository orderRepository,
                        OrderLineItemRepository orderLineItemRepository,
                        ProfileService profileService,
                        ShoppingCartService shoppingCartService)
    {
        this.orderRepository = orderRepository;
        this.orderLineItemRepository = orderLineItemRepository;
        this.profileService = profileService;
        this.shoppingCartService = shoppingCartService;
    }
    // Converts the user's shopping cart into a saved order.
    @Transactional
    public Order checkout(int userId) {
        // Load the user's current cart
        ShoppingCart cart = shoppingCartService.getByUserId(userId);
        // Nothing to check out if the cart is empty
        if (cart.getItems().isEmpty()) {
            return null;
        }
        // We need the profile to pull the shipping address
        Profile profile = profileService.getByUserId(userId);

        if (profile == null) {
            return null;
        }
        // Build the order header with the user's address and the current timestamp
        Order order = new Order();
        order.setUserId(userId);
        order.setDate(LocalDateTime.now());
        order.setAddress(profile.getAddress());
        order.setCity(profile.getCity());
        order.setState(profile.getState());
        order.setZip(profile.getZip());
        order.setShippingAmount(0);
         // Save the order first so we get back the generated orderId
        Order savedOrder = orderRepository.save(order);

        // Create one line item per product in the cart
        // We store the price at the time of purchase so it won't change
        for (Map.Entry<Integer, ShoppingCartItem> entry : cart.getItems().entrySet()) {
            ShoppingCartItem cartItem = entry.getValue();
            OrderLineItem lineItem = new OrderLineItem();
            lineItem.setOrderId(savedOrder.getOrderId());
            lineItem.setProductId(cartItem.getProduct().getProductId());
            lineItem.setSalesPrice(cartItem.getProduct().getPrice());
            lineItem.setQuantity(cartItem.getQuantity());
            lineItem.setDiscount(cartItem.getDiscountPercent());
            orderLineItemRepository.save(lineItem);
        }

        // After checkout, the same cart must not be ordered twice.
        shoppingCartService.clear(userId);

        return savedOrder;
    }
}
