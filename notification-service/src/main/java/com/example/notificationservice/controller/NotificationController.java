package com.example.notificationservice.controller;

import com.example.notificationservice.model.Notification;
import com.example.notificationservice.repository.NotificationRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/notifications")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class NotificationController {

    private final NotificationRepository notificationRepository;
    private final com.example.notificationservice.service.EmailService emailService;

    public NotificationController(NotificationRepository notificationRepository,
            com.example.notificationservice.service.EmailService emailService) {
        this.notificationRepository = notificationRepository;
        this.emailService = emailService;
    }

    @PostMapping
    public ResponseEntity<?> sendNotification(HttpServletRequest request, @RequestBody Notification notification) {
        Long userId = (Long) request.getAttribute("userId");
        if (userId == null)
            return ResponseEntity.status(401).build();

        notification.setUserId(userId);

        try {
            if ("EMAIL".equalsIgnoreCase(notification.getType())) {
                emailService.sendEmail(notification.getRecipient(), "Microservice Notification",
                        notification.getMessage());
            }
            notification.setStatus("SENT");
        } catch (Exception e) {
            notification.setStatus("FAILED: " + e.getMessage());
        }

        return ResponseEntity.ok(notificationRepository.save(notification));
    }

    @GetMapping
    public ResponseEntity<List<Notification>> getMyNotifications(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        if (userId == null)
            return ResponseEntity.status(401).build();

        return ResponseEntity.ok(notificationRepository.findByUserId(userId));
    }

    @GetMapping("/")
    public Map<String, String> health() {
        return Map.of("message", "Notification Service is running", "service", "notification-service");
    }
}
