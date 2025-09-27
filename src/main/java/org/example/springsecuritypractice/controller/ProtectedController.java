package org.example.springsecuritypractice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController // <-- ADD THIS
@RequestMapping("/api") // <-- ADD THIS TO SET A BASE PATH FOR ENDPOINTS
public class ProtectedController {

    @GetMapping("/user/profile")
    public ResponseEntity<String> getUserProfile(Principal principal) {
        String username = principal.getName();
        return ResponseEntity.ok("Hello, User! Your email is: " + username);
    }

    @GetMapping("/admin/stats")
    public ResponseEntity<String> getAdminStats() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String adminName = authentication.getName();
        return ResponseEntity.ok("Hello, Admin " + adminName + "! Here are the stats...");
    }
}
