package com.skillbridge.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationListener {

    @Autowired
    private EmailService emailService;

    @KafkaListener(topics = "roadmap-generated", groupId = "notification-group")
    public void handleRoadmapGenerated(String message) {
        // message format: "email::roadmap"
        String[] parts = message.split("::", 2);
        if (parts.length == 2) {
            String email = parts[0];
            String roadmap = parts[1];
            emailService.sendRoadmapEmail(email, roadmap);
            System.out.println("Email sent to: " + email);
        }
    }
}