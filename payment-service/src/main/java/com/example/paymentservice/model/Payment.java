package com.example.paymentservice.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Data
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long orderId;

    private BigDecimal amount;
    private String status; // SUCCESS, FAILED
    private String method; // CREDIT_CARD, PAYPAL

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
