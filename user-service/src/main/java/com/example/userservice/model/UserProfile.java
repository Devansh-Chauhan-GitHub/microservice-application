package com.example.userservice.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_profiles")
@Data
@NoArgsConstructor
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private Long userId; // From Auth Service

    @Column(unique = true, nullable = false)
    private String email;

    private String name;
    private String phone;
    private String address;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
