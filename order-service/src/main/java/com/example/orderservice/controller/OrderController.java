package com.example.orderservice.controller;

import com.example.orderservice.model.Order;
import com.example.orderservice.repository.OrderRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class OrderController {

    private final OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @PostMapping
    public ResponseEntity<?> createOrder(HttpServletRequest request, @RequestBody Order order) {
        Long userId = (Long) request.getAttribute("userId");
        if (userId == null)
            return ResponseEntity.status(401).build();

        order.setUserId(userId);
        order.setStatus("PENDING");
        return ResponseEntity.ok(orderRepository.save(order));
    }

    @GetMapping
    public ResponseEntity<List<Order>> getMyOrders(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        if (userId == null)
            return ResponseEntity.status(401).build();

        return ResponseEntity.ok(orderRepository.findByUserId(userId));
    }

    @GetMapping("/")
    public Map<String, String> health() {
        return Map.of("message", "Order Service is running", "service", "order-service");
    }
}
