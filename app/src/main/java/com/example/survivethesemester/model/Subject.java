package com.example.survivethesemester.model;

import java.util.ArrayList;
import java.util.List;

public class Subject {
    public final String name, emoji;
    public int readiness; // ความพร้อมสอบ 0-100

    public Subject(String name, String emoji, int readiness) {
        this.name = name; this.emoji = emoji; this.readiness = readiness;
    }

    public void add(int d) { readiness = Math.max(0, Math.min(100, readiness + d)); }

    public static List<Subject> defaults() {
        List<Subject> l = new ArrayList<>();
        l.add(new Subject("Programming", "💻", 72));
        l.add(new Subject("Database", "🗄️", 65));
        l.add(new Subject("Mathematics", "➗", 58));
        l.add(new Subject("English", "🔤", 81));
        l.add(new Subject("Computer Network", "🌐", 70));
        return l;
    }
}
