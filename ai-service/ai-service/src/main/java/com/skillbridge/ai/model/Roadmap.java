package com.skillbridge.ai.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "roadmaps")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Roadmap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userEmail;
    private String currentSkills;
    private String targetRole;

    @Column(columnDefinition = "TEXT")
    private String skillGap;

    @Column(columnDefinition = "TEXT")
    private String roadmapContent;

    private LocalDateTime createdAt = LocalDateTime.now();
}