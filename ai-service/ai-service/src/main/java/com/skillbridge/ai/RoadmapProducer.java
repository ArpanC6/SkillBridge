package com.skillbridge.ai;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.util.Map;

@Component
public class RoadmapProducer {

    @Value("${notification.service.url:http://localhost:8085}")
    private String notificationServiceUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public void sendRoadmapEmail(String email, String roadmap) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            Map<String, String> body = Map.of("email", email, "roadmap", roadmap);
            HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);
            restTemplate.postForEntity(notificationServiceUrl + "/api/notify/roadmap", request, String.class);
            System.out.println("Notification sent via HTTP for: " + email);
        } catch (Exception e) {
            System.out.println("Notification failed (non-critical): " + e.getMessage());
        }
    }
}