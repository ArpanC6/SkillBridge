<div align="center">

# SkillBridge

### AI-Powered Career Navigation Platform

![Java](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)
![Apache Kafka](https://img.shields.io/badge/Apache_Kafka-231F20?style=for-the-badge&logo=apachekafka&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-8-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=jsonwebtokens&logoColor=white)

<br/>

SkillBridge is a career development platform that tells you exactly what skills you are missing, builds you a personalized AI learning roadmap, helps you write a professional resume, and connects you to real job opportunities — all in one place.

</div>

---

## Tech Stack

**Backend**

![Java](https://img.shields.io/badge/Java-ED8B00?style=flat-square&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=flat-square&logo=springboot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring_Security-6DB33F?style=flat-square&logo=springsecurity&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=flat-square&logo=hibernate&logoColor=white)
![Apache Kafka](https://img.shields.io/badge/Apache_Kafka-231F20?style=flat-square&logo=apachekafka&logoColor=white)
![Apache Camel](https://img.shields.io/badge/Apache_Camel-E6522C?style=flat-square&logo=apache&logoColor=white)

**Database & Infrastructure**

![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=mysql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=flat-square&logo=docker&logoColor=white)

**Frontend**

![HTML5](https://img.shields.io/badge/HTML5-E34F26?style=flat-square&logo=html5&logoColor=white)
![CSS3](https://img.shields.io/badge/CSS3-1572B6?style=flat-square&logo=css3&logoColor=white)
![JavaScript](https://img.shields.io/badge/JavaScript-F7DF1E?style=flat-square&logo=javascript&logoColor=black)
![Chart.js](https://img.shields.io/badge/Chart.js-FF6384?style=flat-square&logo=chartdotjs&logoColor=white)

**AI**

![Groq](https://img.shields.io/badge/Groq_AI-F55036?style=flat-square&logo=groq&logoColor=white)
![LLaMA](https://img.shields.io/badge/LLaMA_3.3_70B-0467DF?style=flat-square&logo=meta&logoColor=white)

---

## Features

**AI Roadmap Generator**
Generates a full 3-month, week-by-week learning roadmap using Groq AI. Enter your current skills and target role, and the platform builds a structured study plan with topics and tasks tailored specifically to your skill gap.

**Skill Gap Analysis**
Identifies exactly which skills you are missing for your target role before generating the roadmap, so you know precisely what needs attention.

**AI Career Score**
A readiness percentage calculated from how much of the required skill set for your target role you already have, with honest feedback on where you stand.

**Skill vs Industry Chart**
A radar chart that maps your current skills against what the industry actually expects for your role — built with Chart.js.

**Resume Builder**
Generates a clean, ATS-ready PDF resume. Get AI-written professional summaries and certification recommendations, preview the resume live in the browser, and fill in experience, projects, and technical skills through a structured form.

**Progress Tracker**
Check off weeks as you work through the roadmap. A live progress bar updates in real time to show how far along you are.

**Job Search**
Search real jobs across LinkedIn, Naukri, Indeed, Glassdoor, and Wellfound filtered by skill, role, and country.

**Internship Finder**
A dedicated section for freshers to find internships across Internshala, LinkedIn, RemoteOK, Glassdoor, Naukri, and Wellfound — with filters for remote, hybrid, on-site, paid, and stipend.

**Email Notification**
After a roadmap is generated, a copy is automatically delivered to the user's inbox. The user service publishes a Kafka event and the notification service consumes it and sends the email via JavaMail.

**Dark / Light Mode**
Full theme toggle across every section of the platform.

---

## Architecture

The platform is split into five Spring Boot services. The user-service handles everything related to authentication — registration, login, JWT — and also serves the frontend since the HTML is bundled as a static resource inside it. The ai-service is where all the AI logic lives: it calls the Groq API to generate roadmaps, builds PDF resumes, and returns AI suggestions for resume content. The job-service manages job trend data. The notification-service listens to Kafka and sends emails whenever a user registers or a roadmap is generated. The esb-service uses Apache Camel for event routing between services.

MySQL and Kafka both run as Docker containers alongside the five services. MySQL is configured with a health check so the dependent services only start once the database is fully ready — this was necessary to avoid connection failures on startup. Kafka runs in KRaft mode which means there is no Zookeeper involved, keeping the setup lighter.

---

## Running Locally

The only requirement is Docker Desktop.

```bash
git clone https://github.com/ArpanC6/SkillBridge.git
cd SkillBridge
cp .env.example .env
```

Fill in your `.env`:

```env
DB_USER=root
DB_PASS=your_mysql_password
GROQ_API_KEY=your_groq_api_key
MAIL_USER=your_email@gmail.com
MAIL_PASS=your_gmail_app_password
```

```bash
docker-compose up --build
```

Open `http://localhost:8081`

**Getting API keys:**
- Groq API key → [console.groq.com](https://console.groq.com) (free)
- Gmail App Password → Gmail Settings → Security → App Passwords



## Project Structure

The repository has one folder per service. Each service is a standalone Spring Boot application with its own `pom.xml`, `Dockerfile`, and `src` directory. The `docker-compose.yml` at the root ties everything together — it defines all seven containers (five services, MySQL, and Kafka), sets the environment variables, and manages the startup order using health checks and `depends_on`. The `.env.example` file shows exactly which environment variables need to be set without exposing any actual credentials.

---

<div align="center">

**Arpan Chakraborty**

[![GitHub](https://img.shields.io/badge/GitHub-ArpanC6-181717?style=flat-square&logo=github&logoColor=white)](https://github.com/ArpanC6)
[![Gmail](https://img.shields.io/badge/Gmail-chakrabortyarpan151@gmail.com-EA4335?style=flat-square&logo=gmail&logoColor=white)](mailto:chakrabortyarpan151@gmail.com)

</div>
