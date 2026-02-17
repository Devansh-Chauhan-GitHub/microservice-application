package com.example.authservice.controller;

import com.example.authservice.dto.*;
import com.example.authservice.service.AuthService;
import com.example.authservice.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthService authService, JwtUtil jwtUtil) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest request) {
        try {
            AuthResponse response = authService.signup(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            AuthResponse response = authService.login(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validate(@RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.replace("Bearer ", "");
            if (jwtUtil.validateToken(token)) {
                Long userId = jwtUtil.extractUserId(token);
                String email = jwtUtil.extractEmail(token);
                return ResponseEntity.ok(Map.of("valid", true, "userId", userId, "email", email));
            }
            return ResponseEntity.status(401).body(Map.of("valid", false));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.of("valid", false));
        }
    }

    @GetMapping("/")
    public Map<String, String> health() {
        return Map.of("message", "Auth Service is running", "service", "auth-service");
    }
}
