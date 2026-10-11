package com.example.survivethesemester.model;

import java.util.List;

public class Semester {
    public static final int TOTAL_WEEKS = 4;
    public static final int DAYS_PER_WEEK = 5;   // จันทร์-ศุกร์
    public static final int MAX_ACTIONS = 3;     // Action ต่อวัน
    public static final int WEEKLY_FORGET = 3;   // ลืมเนื้อหาทุกสัปดาห์

    public int week = 1, day = 1, actionsLeft = MAX_ACTIONS;
    public final List<Subject> subjects = Subject.defaults();

    /** ใช้ 1 action คืนค่า true ถ้าขึ้นวันใหม่ */
    public boolean useAction() {
        if (isOver()) return false;
        actionsLeft--;
        if (actionsLeft <= 0) { nextDay(); return true; }
        return false;
    }

    private void nextDay() {
        actionsLeft = MAX_ACTIONS;
        day++;
        if (day > DAYS_PER_WEEK) {
            day = 1;
            week++;
            for (Subject s : subjects) s.add(-WEEKLY_FORGET);
        }
    }

    public boolean isOver() { return week > TOTAL_WEEKS; }

    public String dayName() {
        String[] n = {"จันทร์", "อังคาร", "พุธ", "พฤหัสบดี", "ศุกร์"};
        return n[Math.max(0, Math.min(4, day - 1))];
    }

    public int daysLeft() {
        return (TOTAL_WEEKS - week) * DAYS_PER_WEEK + (DAYS_PER_WEEK - day + 1);
    }
}
