package com.skillbridge.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${MAIL_FROM:chakrabortyarpan224@gmail.com}")
    private String fromEmail;

    public void sendRoadmapEmail(String toEmail, String roadmap) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setSubject("Your SkillBridge Career Roadmap");
        message.setText("Your AI Generated Roadmap:\n\n" + roadmap);
        mailSender.send(message);
    }
}
