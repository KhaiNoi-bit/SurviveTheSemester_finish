package com.example.survivethesemester.controller;

import com.example.survivethesemester.model.Exam;
import com.example.survivethesemester.model.Player;
import com.example.survivethesemester.model.Semester;
import com.example.survivethesemester.model.Subject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ExamController {
    private final Random rnd = new Random();

    /** คะแนนสอบ = ความพร้อมวิชา 80% + ค่าการเรียน 20% + สุ่ม ± 5 - โทษจากเครียด/หมดแรง */
    public List<Exam> takeExam(Player p, Semester s) {
        List<Exam> res = new ArrayList<>();
        for (Subject sub : s.subjects) {
            double score = sub.readiness * 0.8 + p.study * 0.2 + (rnd.nextInt(11) - 5);
            if (p.isStressOverload()) score -= 10;
            else if (p.stress >= 80)  score -= 5;
            if (p.isExhausted())      score -= 5;
            int sc = (int) Math.max(0, Math.min(100, Math.round(score)));
            res.add(new Exam(sub.emoji, sub.name, sc));
        }
        return res;
    }

    public double average(List<Exam> exams) {
        double t = 0;
        for (Exam e : exams) t += e.score;
        return exams.isEmpty() ? 0 : t / exams.size();
    }

    public boolean allPassed(List<Exam> exams) {
        for (Exam e : exams) if (!e.passed()) return false;
        return true;
    }

    /** A=นักศึกษาตัวอย่าง B=เฉียดฉิว C=Working Student D=เกมเมอร์ E=สอบตก */
    public char decideEnding(List<Exam> exams, Player p) {
        double avg = average(exams);
        if (!allPassed(exams)) return 'E';
        if (avg >= 70 && p.happy >= 60 && p.money >= 1000 && p.stress <= 40) return 'A';
        if (p.happy >= 80 && avg < 70) return 'D';
        if (p.money >= 3000) return 'C';
        return 'B';
    }
}
