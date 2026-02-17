package com.example.userservice.controller;

import com.example.userservice.model.UserProfile;
import com.example.userservice.repository.UserProfileRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class UserController {

    private final UserProfileRepository userProfileRepository;

    public UserController(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    @GetMapping("/me")
    public ResponseEntity<?> getMyProfile(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        String email = (String) request.getAttribute("email");
        String name = (String) request.getAttribute("name");

        if (userId == null)
            return ResponseEntity.status(401).build();

        return ResponseEntity.ok(userProfileRepository.findByUserId(userId)
                .orElseGet(() -> {
                    // Create default profile if not exists
                    UserProfile profile = new UserProfile();
                    profile.setUserId(userId);
                    profile.setEmail(email);
                    profile.setName(name != null ? name : "User " + userId);
                    return userProfileRepository.save(profile);
                }));
    }

    @PutMapping("/me")
    public ResponseEntity<?> updateMyProfile(HttpServletRequest request, @RequestBody UserProfile updatedProfile) {
        Long userId = (Long) request.getAttribute("userId");
        if (userId == null)
            return ResponseEntity.status(401).build();

        return userProfileRepository.findByUserId(userId)
                .map(profile -> {
                    profile.setName(updatedProfile.getName());
                    profile.setPhone(updatedProfile.getPhone());
                    profile.setAddress(updatedProfile.getAddress());
                    return ResponseEntity.ok(userProfileRepository.save(profile));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/")
    public Map<String, String> health() {
        return Map.of("message", "User Service is running", "service", "user-service");
    }
}
