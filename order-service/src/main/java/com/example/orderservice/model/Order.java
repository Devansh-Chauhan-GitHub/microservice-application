package com.example.orderservice.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    private String item;
    private Integer quantity;
    private BigDecimal totalPrice;
    private String status; // PENDING, COMPLETED, CANCELLED

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
