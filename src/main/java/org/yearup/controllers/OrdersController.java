package org.yearup.controllers;
import org.yearup.models.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.models.User;
import org.yearup.service.OrderService;
import org.yearup.service.UserService;

import java.security.Principal;

@RestController
@RequestMapping("orders")
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class OrdersController {
    private final OrderService orderService;
    private final UserService userService;

    public OrdersController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }
    // Handles POST /orders — triggers checkout for the currently logged-in user
    @PostMapping("")
    public ResponseEntity<Order> checkout(Principal principal) {
        // Resolve the principal to a userId before passing it to the service
        int userId = getCurrentUserId(principal);
        Order order = orderService.checkout(userId);
        // The service returns null if the cart is empty or the profile is missing
        if (order == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cart is empty or profile is missing.");
        }
        // Return the saved order with 201 Created
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    // Pulls the userId from the security principal so we don't pass
    // the username string all the way down into the service layer
    private int getCurrentUserId(Principal principal) {
        if (principal == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login required.");
        }
        String userName = principal.getName();
        User user = userService.getByUserName(userName);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login required.");
        }
        return user.getId();
    }
}
