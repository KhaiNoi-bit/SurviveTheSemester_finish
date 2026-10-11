package com.example.survivethesemester.model;

public class Exam {
    public static final int PASS_SCORE = 50;
    public final String emoji, subject;
    public final int score;

    public Exam(String emoji, String subject, int score) {
        this.emoji = emoji; this.subject = subject; this.score = score;
    }
    public boolean passed() { return score >= PASS_SCORE; }
}
