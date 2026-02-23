package com.skillbridge.ai.controller;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.ByteArrayOutputStream;
import java.time.Duration;
import java.util.*;

@RestController
@RequestMapping("/api/resume")
@CrossOrigin(origins = "*")
public class ResumeController {

    @Value("${groq.api.key}")
    private String groqApiKey;

    @Value("${groq.model}")
    private String groqModel;

    private static final BaseColor C_BLACK = new BaseColor(10, 10, 10);
    private static final BaseColor C_DARK  = new BaseColor(30, 41, 59);
    private static final BaseColor C_GRAY  = new BaseColor(100, 116, 139);
    private static final BaseColor C_LINE  = new BaseColor(170, 170, 170);

    @PostMapping("/generate")
    public ResponseEntity<byte[]> generateResume(@RequestBody Map<String, Object> req) {
        try {
            byte[] pdf = buildPdf(req);
            HttpHeaders h = new HttpHeaders();
            h.setContentType(MediaType.APPLICATION_PDF);
            h.setContentDispositionFormData("attachment", "Resume.pdf");
            return ResponseEntity.ok().headers(h).body(pdf);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/suggest")
    public ResponseEntity<Map<String, String>> suggest(@RequestBody Map<String, String> req) {
        String skills     = req.getOrDefault("skills", "");
        String targetRole = req.getOrDefault("targetRole", "");
        String experience = req.getOrDefault("experience", "");

        String prompt =
                "You are a professional resume coach. Based on the details below, give concise suggestions.\n" +
                        "Target Role: " + targetRole + "\nSkills: " + skills + "\nExperience: " + experience + "\n\n" +
                        "Reply ONLY with a raw JSON object (no markdown, no extra text):\n" +
                        "{\"summary\":\"...\",\"certifications\":\"...\",\"achievements\":\"...\",\"skillTip\":\"...\"}";

        try {
            WebClient client = WebClient.builder()
                    .baseUrl("https://api.groq.com")
                    .defaultHeader("Authorization", "Bearer " + groqApiKey)
                    .defaultHeader("Content-Type", "application/json")
                    .build();

            String raw = client.post()
                    .uri("/openai/v1/chat/completions")
                    .bodyValue(java.util.Map.of(
                            "model", groqModel,
                            "messages", java.util.List.of(java.util.Map.of("role", "user", "content", prompt)),
                            "max_tokens", 600
                    ))
                    .retrieve()
                    .bodyToMono(Map.class)
                    .timeout(Duration.ofSeconds(25))
                    .map(res -> {
                        @SuppressWarnings("unchecked")
                        java.util.List<Object> choices = (java.util.List<Object>) res.get("choices");
                        @SuppressWarnings("unchecked")
                        Map<String, Object> choice = (Map<String, Object>) choices.get(0);
                        @SuppressWarnings("unchecked")
                        Map<String, Object> message = (Map<String, Object>) choice.get("message");
                        return (String) message.get("content");
                    })
                    .block();

            if (raw == null) throw new RuntimeException("null response");
            raw = raw.replaceAll("```json", "").replaceAll("```", "").trim();

            Map<String, String> result = new HashMap<>();
            result.put("summary",        extractJson(raw, "summary"));
            result.put("certifications", extractJson(raw, "certifications"));
            result.put("achievements",   extractJson(raw, "achievements"));
            result.put("skillTip",       extractJson(raw, "skillTip"));
            return ResponseEntity.ok(result);

        } catch (Exception e) {
            Map<String, String> fallback = new HashMap<>();
            fallback.put("summary",        "Results-driven " + targetRole + " with expertise in " + skills + ".");
            fallback.put("certifications", "Consider certifications relevant to " + targetRole + ".");
            fallback.put("achievements",   "Quantify achievements with numbers and measurable impact.");
            fallback.put("skillTip",       "Highlight the most in-demand skills for " + targetRole + " roles.");
            return ResponseEntity.ok(fallback);
        }
    }

    @SuppressWarnings("unchecked")
    private byte[] buildPdf(Map<String, Object> r) throws Exception {

        BaseFont hv  = BaseFont.createFont(BaseFont.HELVETICA,         BaseFont.CP1252, false);
        BaseFont hvB = BaseFont.createFont(BaseFont.HELVETICA_BOLD,    BaseFont.CP1252, false);
        BaseFont hvI = BaseFont.createFont(BaseFont.HELVETICA_OBLIQUE, BaseFont.CP1252, false);

        Font fName    = new Font(hvB,  20,   Font.NORMAL, C_BLACK);
        Font fContact = new Font(hv,    9,   Font.NORMAL, C_DARK);
        Font fSecHead = new Font(hvB,   9,   Font.NORMAL, C_BLACK);
        Font fBold    = new Font(hvB,   9,   Font.NORMAL, C_BLACK);
        Font fItalic  = new Font(hvI,   9,   Font.NORMAL, C_GRAY);
        Font fBody    = new Font(hv,    9,   Font.NORMAL, C_DARK);
        Font fFooter  = new Font(hvI,  7.5f, Font.NORMAL, C_GRAY);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document doc = new Document(PageSize.A4, 54, 54, 42, 36);
        PdfWriter.getInstance(doc, baos);
        doc.open();

        // Name
        Paragraph pName = new Paragraph(s(r, "name", "Your Name"), fName);
        pName.setAlignment(Element.ALIGN_CENTER);
        pName.setSpacingAfter(3);
        doc.add(pName);

        // Contact line
        java.util.List<String> contacts = new ArrayList<>();
        addIf(contacts, s(r, "location", ""));
        addIf(contacts, s(r, "phone", ""));
        addIf(contacts, s(r, "email", ""));
        addIf(contacts, s(r, "linkedin", ""));
        addIf(contacts, s(r, "github", ""));
        addIf(contacts, s(r, "leetcode", ""));
        addIf(contacts, s(r, "codeforces", ""));
        addIf(contacts, s(r, "codechef", ""));
        addIf(contacts, s(r, "hackerrank", ""));
        addIf(contacts, s(r, "gfg", ""));
        addIf(contacts, s(r, "portfolio", ""));
        if (!contacts.isEmpty()) {
            Paragraph pc = new Paragraph(String.join(" | ", contacts), fContact);
            pc.setAlignment(Element.ALIGN_CENTER);
            pc.setSpacingAfter(6);
            doc.add(pc);
        }

        // Summary
        String summary = s(r, "summary", "");
        if (!summary.isEmpty()) {
            sec(doc, "Summary", fSecHead);
            Paragraph ps = new Paragraph(summary.trim(), fBody);
            ps.setLeading(13.5f);
            ps.setSpacingAfter(4);
            doc.add(ps);
        }

        // Education
        String university = s(r, "university", "");
        String degree     = s(r, "degree", "");
        if (!university.isEmpty() || !degree.isEmpty()) {
            sec(doc, "Education", fSecHead);
            twoCol(doc, university, s(r, "uniLocation", ""), fBold,   fItalic);
            twoCol(doc, degree,     s(r, "gradPeriod",  ""), fItalic, fItalic);
            gap(doc, 4);
        }

        // Experience
        java.util.List<Map<String, String>> exps =
                (java.util.List<Map<String, String>>) r.getOrDefault("experiences", Collections.emptyList());
        if (!exps.isEmpty()) {
            sec(doc, "Experience", fSecHead);
            for (Map<String, String> e : exps) {
                twoCol(doc, e.getOrDefault("title",    ""), e.getOrDefault("period",   ""), fBold,   fItalic);
                twoCol(doc, e.getOrDefault("company",  ""), e.getOrDefault("location", ""), fItalic, fItalic);
                bullets(doc, e.getOrDefault("description", ""), fBody);
                gap(doc, 3);
            }
        }

        // Projects
        java.util.List<Map<String, String>> projs =
                (java.util.List<Map<String, String>>) r.getOrDefault("projects", Collections.emptyList());
        if (!projs.isEmpty()) {
            sec(doc, "Projects", fSecHead);
            for (Map<String, String> p : projs) {
                String tech = p.getOrDefault("tech", "");
                String left = p.getOrDefault("name", "") + (tech.isEmpty() ? "" : " | " + tech);
                twoCol(doc, left, p.getOrDefault("period", ""), fBold, fItalic);
                bullets(doc, p.getOrDefault("description", ""), fBody);
                gap(doc, 3);
            }
        }

        // Technical Skills
        Map<String, String> skillsMap =
                (Map<String, String>) r.getOrDefault("skillsMap", Collections.emptyMap());
        String flatSkills = s(r, "skills", "");
        if (!skillsMap.isEmpty()) {
            sec(doc, "Technical Skills", fSecHead);
            for (Map.Entry<String, String> entry : skillsMap.entrySet()) {
                if (entry.getValue() == null || entry.getValue().trim().isEmpty()) continue;
                Paragraph sp = new Paragraph();
                sp.add(new Chunk(entry.getKey() + ": ", fBold));
                sp.add(new Chunk(entry.getValue(), fBody));
                sp.setLeading(13);
                doc.add(sp);
            }
            gap(doc, 4);
        } else if (!flatSkills.isEmpty()) {
            sec(doc, "Technical Skills", fSecHead);
            doc.add(new Paragraph(flatSkills, fBody));
            gap(doc, 4);
        }

        // Certifications
        String certs = s(r, "certifications", "");
        if (!certs.isEmpty()) {
            sec(doc, "Certifications", fSecHead);
            bullets(doc, certs, fBody);
            gap(doc, 4);
        }

        // Achievements
        String achievements = s(r, "achievements", "");
        if (!achievements.isEmpty()) {
            sec(doc, "Achievements", fSecHead);
            bullets(doc, achievements, fBody);
            gap(doc, 4);
        }

        // Footer
        LineSeparator fl = new LineSeparator(0.5f, 100, C_LINE, Element.ALIGN_CENTER, -2);
        doc.add(new Chunk(fl));
        Paragraph pf = new Paragraph("Generated by SkillBridge AI", fFooter);
        pf.setAlignment(Element.ALIGN_CENTER);
        pf.setSpacingBefore(3);
        doc.add(pf);

        doc.close();
        return baos.toByteArray();
    }

    private void sec(Document doc, String title, Font f) throws DocumentException {
        gap(doc, 4);
        Paragraph p = new Paragraph(title.toUpperCase(), f);
        p.setSpacingAfter(1);
        doc.add(p);
        doc.add(new Chunk(new LineSeparator(0.8f, 100, C_BLACK, Element.ALIGN_LEFT, -1)));
        gap(doc, 3);
    }

    private void twoCol(Document doc, String left, String right,
                        Font lf, Font rf) throws DocumentException {
        if (left.isEmpty() && right.isEmpty()) return;
        PdfPTable t = new PdfPTable(2);
        t.setWidthPercentage(100);
        t.setWidths(new float[]{72, 28});
        PdfPCell lc = new PdfPCell(new Paragraph(left, lf));
        lc.setBorder(Rectangle.NO_BORDER);
        lc.setPadding(1);
        PdfPCell rc = new PdfPCell(new Paragraph(right, rf));
        rc.setBorder(Rectangle.NO_BORDER);
        rc.setPadding(1);
        rc.setHorizontalAlignment(Element.ALIGN_RIGHT);
        t.addCell(lc);
        t.addCell(rc);
        t.setSpacingAfter(1);
        doc.add(t);
    }

    private void bullets(Document doc, String text, Font f) throws DocumentException {
        if (text == null || text.trim().isEmpty()) return;
        for (String line : text.split("\n")) {
            String t = line.trim().replaceAll("^[-\u2022*]\\s*", "");
            if (t.isEmpty()) continue;
            Paragraph p = new Paragraph("\u2022  " + t, f);
            p.setIndentationLeft(12);
            p.setFirstLineIndent(-12);
            p.setLeading(13.5f);
            doc.add(p);
        }
    }

    private void gap(Document doc, float pt) throws DocumentException {
        Paragraph p = new Paragraph(" ");
        p.setLeading(pt);
        doc.add(p);
    }

    private void addIf(java.util.List<String> list, String val) {
        if (val != null && !val.trim().isEmpty()) list.add(val.trim());
    }

    private String s(Map<String, Object> map, String key, String def) {
        Object v = map.get(key);
        if (v == null) return def;
        String str = v.toString().trim();
        return str.isEmpty() ? def : str;
    }

    private String extractJson(String json, String key) {
        try {
            int idx = json.indexOf("\"" + key + "\"");
            if (idx == -1) return "";
            int colon = json.indexOf(":", idx);
            int q1    = json.indexOf("\"", colon + 1);
            int q2    = json.indexOf("\"", q1 + 1);
            while (q2 > 0 && json.charAt(q2 - 1) == '\\') {
                q2 = json.indexOf("\"", q2 + 1);
            }
            return json.substring(q1 + 1, q2)
                    .replace("\\n", " ")
                    .replace("\\\"", "\"");
        } catch (Exception e) {
            return "";
        }
    }
}