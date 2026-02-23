package com.skillbridge.job.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "job_trends")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobTrend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String skillName;
    private String jobRole;
    private Integer demandCount;
    private String location;
    private String salaryRange;
    private String applyUrl;
    private LocalDateTime updatedAt = LocalDateTime.now();
}