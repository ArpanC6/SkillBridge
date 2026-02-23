package com.skillbridge.notification;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.*;

@Service
public class EmailService {

    @Value("${MAIL_PASS}")
    private String apiKey;

    public void sendRoadmapEmail(String toEmail, String roadmap) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        Map<String, Object> body = new HashMap<>();
        body.put("personalizations", List.of(Map.of("to", List.of(Map.of("email", toEmail)))));
        body.put("from", Map.of("email", "chakrabortyarpan224@gmail.com"));
        body.put("subject", "Your SkillBridge Career Roadmap");
        body.put("content", List.of(Map.of("type", "text/plain", "value", "Your AI Generated Roadmap:\n\n" + roadmap)));

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        restTemplate.postForEntity("https://api.sendgrid.com/v3/mail/send", request, String.class);
    }
}
