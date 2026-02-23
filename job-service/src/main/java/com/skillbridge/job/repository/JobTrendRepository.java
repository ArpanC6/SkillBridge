package com.skillbridge.job.repository;

import com.skillbridge.job.model.JobTrend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface JobTrendRepository extends JpaRepository<JobTrend, Long> {
    List<JobTrend> findBySkillName(String skillName);
    List<JobTrend> findByJobRole(String jobRole);
}