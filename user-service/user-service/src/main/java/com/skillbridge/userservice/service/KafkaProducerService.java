package com.skillbridge.userservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendUserEvent(String message) {
        kafkaTemplate.send("user-events", message);
        System.out.println("Kafka Event Sent: " + message);
    }
}