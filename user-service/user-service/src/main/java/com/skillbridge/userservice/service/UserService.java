package com.skillbridge.userservice.service;

import com.skillbridge.userservice.dto.LoginRequest;
import com.skillbridge.userservice.dto.RegisterRequest;
import com.skillbridge.userservice.model.User;
import com.skillbridge.userservice.repository.UserRepository;
import com.skillbridge.userservice.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final KafkaProducerService kafkaProducerService;

    public User register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists!");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCurrentSkills(request.getCurrentSkills());
        user.setTargetRole(request.getTargetRole());
        user.setEducationLevel(request.getEducationLevel());
        user.setCountry(request.getCountry());

        User savedUser = userRepository.save(user);

        // Kafka Event Send
        kafkaProducerService.sendUserEvent(
                "NEW_USER:" + savedUser.getEmail() + ":" + savedUser.getTargetRole()
        );

        return savedUser;
    }

    public String login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found!"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password!");
        }

        return jwtUtil.generateToken(user.getEmail());
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found!"));
    }
}