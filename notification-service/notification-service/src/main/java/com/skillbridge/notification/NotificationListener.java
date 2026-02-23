package com.skillbridge.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/notify")
public class NotificationListener {

    @Autowired
    private EmailService emailService;

    @PostMapping("/roadmap")
    public ResponseEntity<String> handleRoadmapGenerated(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String roadmap = body.get("roadmap");
        if (email != null && roadmap != null) {
            emailService.sendRoadmapEmail(email, roadmap);
            System.out.println("Email sent to: " + email);
            return ResponseEntity.ok("Email sent");
        }
        return ResponseEntity.badRequest().body("Missing email or roadmap");
    }
}