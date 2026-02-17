package com.example.notificationservice.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Data
@NoArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    private String type; // EMAIL, SMS
    private String recipient;
    private String message;
    private String status; // SENT, FAILED

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
