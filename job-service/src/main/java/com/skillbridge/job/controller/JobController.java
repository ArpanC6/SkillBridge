package com.skillbridge.job.controller;

import com.skillbridge.job.model.JobTrend;
import com.skillbridge.job.service.JobMarketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobMarketService jobMarketService;

    @GetMapping("/trends")
    public ResponseEntity<List<JobTrend>> getAllTrends() {
        return ResponseEntity.ok(jobMarketService.getAllTrends());
    }

    @GetMapping("/trends/skill/{skillName}")
    public ResponseEntity<List<JobTrend>> getBySkill(@PathVariable String skillName) {
        return ResponseEntity.ok(jobMarketService.getTrendsBySkill(skillName));
    }

    @GetMapping("/trends/role/{jobRole}")
    public ResponseEntity<List<JobTrend>> getByRole(@PathVariable String jobRole) {
        return ResponseEntity.ok(jobMarketService.getTrendsByRole(jobRole));
    }

    @PostMapping("/mock-data")
    public ResponseEntity<String> loadMockData() {
        jobMarketService.loadMockData();
        return ResponseEntity.ok("Mock data loaded!");
    }
}