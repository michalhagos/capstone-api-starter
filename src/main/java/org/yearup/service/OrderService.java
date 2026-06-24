package org.yearup.service;
import org.springframework.stereotype.Service;
import org.yearup.repository.OrderLineItemRepository;
import org.yearup.repository.OrderRepository;

@Service
public class OrderService {
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
}
