package com.example.paymentservice.controller;

import com.example.paymentservice.model.Payment;
import com.example.paymentservice.repository.PaymentRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/payments")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class PaymentController {

    private final PaymentRepository paymentRepository;

    public PaymentController(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @PostMapping
    public ResponseEntity<?> processPayment(HttpServletRequest request, @RequestBody Payment payment) {
        Long userId = (Long) request.getAttribute("userId");
        if (userId == null)
            return ResponseEntity.status(401).build();

        payment.setUserId(userId);
        payment.setStatus("SUCCESS"); // Simulated success
        return ResponseEntity.ok(paymentRepository.save(payment));
    }

    @GetMapping
    public ResponseEntity<List<Payment>> getMyPayments(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        if (userId == null)
            return ResponseEntity.status(401).build();

        return ResponseEntity.ok(paymentRepository.findByUserId(userId));
    }

    @GetMapping("/")
    public Map<String, String> health() {
        return Map.of("message", "Payment Service is running", "service", "payment-service");
    }
}
