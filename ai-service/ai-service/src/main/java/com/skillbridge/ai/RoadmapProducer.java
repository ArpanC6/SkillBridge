package com.skillbridge.ai;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class RoadmapProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendRoadmapEmail(String email, String roadmap) {
        String message = email + "::" + roadmap;
        kafkaTemplate.send("roadmap-generated", message);
        System.out.println("Kafka message sent for: " + email);
    }
}