package com.skillbridge.ai.controller;

import com.skillbridge.ai.model.Roadmap;
import com.skillbridge.ai.service.SkillGapService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AIController {

    private final SkillGapService skillGapService;

    @PostMapping("/roadmap")
    public ResponseEntity<Roadmap> generateRoadmap(
            @RequestParam String email,
            @RequestParam String currentSkills,
            @RequestParam String targetRole) {

        Roadmap roadmap = skillGapService.generateRoadmap(email, currentSkills, targetRole);
        return ResponseEntity.ok(roadmap);
    }

    @GetMapping("/roadmap/{email}")
    public ResponseEntity<List<Roadmap>> getRoadmaps(@PathVariable String email) {
        List<Roadmap> roadmaps = skillGapService.getRoadmapsByEmail(email);
        return ResponseEntity.ok(roadmaps);
    }
}