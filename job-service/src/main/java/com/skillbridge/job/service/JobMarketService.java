package com.skillbridge.job.service;

import com.skillbridge.job.model.JobTrend;
import com.skillbridge.job.repository.JobTrendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JobMarketService {

    private final JobTrendRepository jobTrendRepository;

    public JobTrend addJobTrend(JobTrend jobTrend) {
        return jobTrendRepository.save(jobTrend);
    }

    public List<JobTrend> getAllTrends() {
        loadMockData();
        return jobTrendRepository.findAll();
    }

    public List<JobTrend> getTrendsBySkill(String skillName) {
        return jobTrendRepository.findBySkillName(skillName);
    }

    public List<JobTrend> getTrendsByRole(String jobRole) {
        return jobTrendRepository.findByJobRole(jobRole);
    }

    public void loadMockData() {
        if (jobTrendRepository.count() == 0) {

            // ── India ──
            save("Java",           "Backend Developer",       1500, "India",         "₹6-12 LPA",  "https://www.linkedin.com/jobs/search/?keywords=Java+Backend+Developer&location=India");
            save("Spring Boot",    "Backend Developer",       1200, "India",         "₹7-14 LPA",  "https://www.naukri.com/spring-boot-developer-jobs");
            save("Python",         "Data Scientist",          1800, "India",         "₹8-18 LPA",  "https://www.linkedin.com/jobs/search/?keywords=Python+Data+Scientist&location=India");
            save("React",          "Frontend Developer",      1300, "India",         "₹5-12 LPA",  "https://www.naukri.com/react-developer-jobs");
            save("Node.js",        "Full Stack Developer",    1100, "India",         "₹7-15 LPA",  "https://www.linkedin.com/jobs/search/?keywords=Nodejs+Full+Stack&location=India");
            save("AWS",            "Cloud Engineer",           900, "India",         "₹10-20 LPA", "https://www.naukri.com/aws-cloud-engineer-jobs");
            save("Docker",         "DevOps Engineer",          850, "India",         "₹8-16 LPA",  "https://www.linkedin.com/jobs/search/?keywords=Docker+DevOps&location=India");
            save("Machine Learning","ML Engineer",             950, "India",         "₹10-22 LPA", "https://www.naukri.com/machine-learning-engineer-jobs");
            save("Flutter",        "Mobile Developer",         750, "India",         "₹5-12 LPA",  "https://www.linkedin.com/jobs/search/?keywords=Flutter+Developer&location=India");
            save("SQL",            "Database Administrator",   700, "India",         "₹5-10 LPA",  "https://www.naukri.com/sql-database-administrator-jobs");
            save("Cybersecurity",  "Security Analyst",         600, "India",         "₹8-15 LPA",  "https://www.linkedin.com/jobs/search/?keywords=Cybersecurity+Analyst&location=India");
            save("Kotlin",         "Android Developer",        650, "India",         "₹6-13 LPA",  "https://www.naukri.com/android-developer-jobs");

            // ── Bangladesh ──
            save("Java",           "Backend Developer",        800, "Bangladesh",    "৳50-80K/mo", "https://www.linkedin.com/jobs/search/?keywords=Java+Backend&location=Bangladesh");
            save("Python",         "Data Analyst",             700, "Bangladesh",    "৳45-70K/mo", "https://www.linkedin.com/jobs/search/?keywords=Python+Data+Analyst&location=Bangladesh");
            save("React",          "Frontend Developer",       750, "Bangladesh",    "৳40-65K/mo", "https://www.linkedin.com/jobs/search/?keywords=React+Developer&location=Bangladesh");
            save("PHP",            "Web Developer",            600, "Bangladesh",    "৳30-55K/mo", "https://www.linkedin.com/jobs/search/?keywords=PHP+Developer&location=Bangladesh");
            save("Flutter",        "Mobile Developer",         500, "Bangladesh",    "৳45-70K/mo", "https://www.linkedin.com/jobs/search/?keywords=Flutter+Developer&location=Bangladesh");

            // ── United States ──
            save("Java",           "Senior Backend Engineer", 5000, "United States", "$100-150K/yr","https://www.linkedin.com/jobs/search/?keywords=Java+Backend+Engineer&location=United+States");
            save("Python",         "Data Scientist",          6000, "United States", "$110-160K/yr","https://www.indeed.com/jobs?q=Python+Data+Scientist&l=United+States");
            save("React",          "Frontend Engineer",       4500, "United States", "$90-140K/yr", "https://www.linkedin.com/jobs/search/?keywords=React+Frontend+Engineer&location=United+States");
            save("AWS",            "Cloud Architect",         3500, "United States", "$130-180K/yr","https://www.indeed.com/jobs?q=AWS+Cloud+Architect&l=United+States");
            save("Machine Learning","ML Engineer",            4000, "United States", "$120-175K/yr","https://www.linkedin.com/jobs/search/?keywords=Machine+Learning+Engineer&location=United+States");
            save("Cybersecurity",  "Security Engineer",       3000, "United States", "$110-160K/yr","https://www.indeed.com/jobs?q=Cybersecurity+Engineer&l=United+States");
            save("Node.js",        "Full Stack Engineer",     4200, "United States", "$95-145K/yr", "https://www.linkedin.com/jobs/search/?keywords=Nodejs+Full+Stack&location=United+States");
            save("Kubernetes",     "DevOps Engineer",         2800, "United States", "$120-170K/yr","https://www.indeed.com/jobs?q=Kubernetes+DevOps&l=United+States");

            // ── United Kingdom ──
            save("Java",           "Backend Developer",       2200, "United Kingdom","£50-80K/yr",  "https://www.linkedin.com/jobs/search/?keywords=Java+Backend+Developer&location=United+Kingdom");
            save("Python",         "Data Engineer",           2500, "United Kingdom","£55-85K/yr",  "https://www.indeed.co.uk/jobs?q=Python+Data+Engineer");
            save("React",          "Frontend Developer",      2000, "United Kingdom","£45-75K/yr",  "https://www.linkedin.com/jobs/search/?keywords=React+Developer&location=United+Kingdom");
            save("AWS",            "Cloud Engineer",          1800, "United Kingdom","£60-90K/yr",  "https://www.indeed.co.uk/jobs?q=AWS+Cloud+Engineer");

            // ── Canada ──
            save("Java",           "Backend Developer",       1800, "Canada",        "CA$80-120K/yr","https://www.linkedin.com/jobs/search/?keywords=Java+Backend+Developer&location=Canada");
            save("Python",         "Data Scientist",          2000, "Canada",        "CA$85-130K/yr","https://www.indeed.ca/jobs?q=Python+Data+Scientist");
            save("React",          "Frontend Developer",      1600, "Canada",        "CA$75-110K/yr","https://www.linkedin.com/jobs/search/?keywords=React+Developer&location=Canada");
            save("AWS",            "Cloud Engineer",          1400, "Canada",        "CA$90-135K/yr","https://www.indeed.ca/jobs?q=AWS+Cloud+Engineer");

            // ── Germany ──
            save("Java",           "Backend Developer",       1500, "Germany",       "€55-85K/yr",  "https://www.linkedin.com/jobs/search/?keywords=Java+Backend+Developer&location=Germany");
            save("Python",         "Data Scientist",          1400, "Germany",       "€60-90K/yr",  "https://www.indeed.de/jobs?q=Python+Data+Scientist");
            save("React",          "Frontend Developer",      1300, "Germany",       "€50-80K/yr",  "https://www.linkedin.com/jobs/search/?keywords=React+Developer&location=Germany");

            // ── Singapore ──
            save("Java",           "Backend Developer",       1200, "Singapore",     "S$70-110K/yr","https://www.linkedin.com/jobs/search/?keywords=Java+Backend+Developer&location=Singapore");
            save("Python",         "Data Scientist",          1100, "Singapore",     "S$75-120K/yr","https://www.indeed.com/jobs?q=Python+Data+Scientist&l=Singapore");
            save("React",          "Frontend Developer",      1000, "Singapore",     "S$65-100K/yr","https://www.linkedin.com/jobs/search/?keywords=React+Developer&location=Singapore");
            save("AWS",            "Cloud Engineer",           900, "Singapore",     "S$80-125K/yr","https://www.indeed.com/jobs?q=AWS+Cloud+Engineer&l=Singapore");

            // ── UAE ──
            save("Java",           "Backend Developer",        900, "UAE",           "AED 15-25K/mo","https://www.linkedin.com/jobs/search/?keywords=Java+Backend+Developer&location=United+Arab+Emirates");
            save("React",          "Frontend Developer",       800, "UAE",           "AED 12-22K/mo","https://www.bayt.com/en/uae/jobs/react-developer-jobs/");
            save("Python",         "Data Analyst",             750, "UAE",           "AED 14-24K/mo","https://www.linkedin.com/jobs/search/?keywords=Python+Data+Analyst&location=UAE");

            // ── Remote / Worldwide ──
            save("Java",           "Remote Backend Engineer", 3000, "Remote",        "$60-130K/yr", "https://remote.co/remote-jobs/developer/");
            save("Python",         "Remote Data Scientist",   3500, "Remote",        "$70-140K/yr", "https://www.wellfound.com/jobs?role=Data+Scientist&remote=true");
            save("React",          "Remote Frontend Engineer",2800, "Remote",        "$55-120K/yr", "https://remote.co/remote-jobs/developer/");
            save("Node.js",        "Remote Full Stack Dev",   2600, "Remote",        "$60-125K/yr", "https://www.wellfound.com/jobs?role=Full+Stack+Engineer&remote=true");
            save("AWS",            "Remote Cloud Engineer",   2200, "Remote",        "$80-150K/yr", "https://remote.co/remote-jobs/sysadmin/");
            save("Machine Learning","Remote ML Engineer",     2400, "Remote",        "$85-155K/yr", "https://www.wellfound.com/jobs?role=Machine+Learning+Engineer&remote=true");

            // ── Internships (Freshers) ──
            save("Java",           "Software Intern",          500, "India (Intern)", "₹10-25K/mo", "https://internshala.com/internships/java-internship");
            save("Python",         "Data Science Intern",      600, "India (Intern)", "₹10-25K/mo", "https://internshala.com/internships/data-science-internship");
            save("React",          "Frontend Intern",          550, "India (Intern)", "₹8-20K/mo",  "https://internshala.com/internships/web-development-internship");
            save("Machine Learning","ML Intern",               480, "India (Intern)", "₹12-25K/mo", "https://internshala.com/internships/machine-learning-internship");
        }
    }

    private void save(String skill, String role, int demand, String location, String salary, String url) {
        jobTrendRepository.save(new JobTrend(null, skill, role, demand, location, salary, url, null));
    }
}