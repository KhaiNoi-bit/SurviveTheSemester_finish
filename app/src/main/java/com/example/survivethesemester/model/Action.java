package com.example.survivethesemester.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/** กิจกรรมประจำวัน (ชื่อ Action เพื่อไม่ให้ชนกับ android.app.Activity) */
public class Action {
    public final String emoji, name;
    public final int study, energy, happy, stress, money;
    public final boolean studyType;   // ต้องเลือกวิชา
    public final int subjectGain;     // เพิ่มความพร้อมสอบของวิชา
    public final boolean needsEnergy; // ถ้าพลังงาน 0 จะถูกล็อก

    public Action(String emoji, String name, int study, int energy, int happy, int stress, int money,
                  boolean studyType, int subjectGain, boolean needsEnergy) {
        this.emoji = emoji; this.name = name;
        this.study = study; this.energy = energy; this.happy = happy; this.stress = stress; this.money = money;
        this.studyType = studyType; this.subjectGain = subjectGain; this.needsEnergy = needsEnergy;
    }

    public static List<Action> defaults() {
        List<Action> l = new ArrayList<>();
        //                    emoji  ชื่อ             📚   ⚡   😊   😰   💰   วิชา  +วิชา  ใช้แรง
        l.add(new Action("🏫", "เข้าเรียน",        16,  -9,   4,   6,   0, true,  12, true));
        l.add(new Action("📖", "อ่านหนังสือ",      20, -12,   0,   8,   0, true,  14, true));
        l.add(new Action("📝", "ทำงานกลุ่ม",       10,  -8,   8,   4,   0, true,   9, true));
        l.add(new Action("💼", "ทำงานพิเศษ",       0, -16,   2,  10, 130, false,  0, true));
        l.add(new Action("🎮", "เล่นเกม",          -6,  -8,  16,  -8,   0, false,  0, false));
        l.add(new Action("😴", "นอน",              -2,  24,   8, -10,   0, false,  0, false));
        l.add(new Action("🏃", "ออกกำลังกาย",      0, -10,  10,  -6,   0, false,  0, false));
        return l;
    }

    public static List<Action> randomizedDefaults() {
        Random r = new Random();
        List<Action> l = new ArrayList<>();
        for (Action base : defaults()) {
            int money = base.name.equals("ทำงานพิเศษ")
                    ? 100 + r.nextInt(61)   // 100-160
                    : randomize(base.money, r);
            l.add(new Action(
                    base.emoji,
                    base.name,
                    randomize(base.study, r),
                    randomize(base.energy, r),
                    randomize(base.happy, r),
                    randomize(base.stress, r),
                    money,
                    base.studyType,
                    base.subjectGain,
                    base.needsEnergy
            ));
        }
        return l;
    }

    private static int randomize(int value, Random r) {
        if (value == 0) return 0;
        int spread = Math.max(2, Math.abs(value) / 3);
        int offset = r.nextInt(spread * 2 + 1) - spread;
        int adjusted = value + offset;

        if (value > 0) return Math.max(0, Math.min(28, adjusted));
        return Math.min(0, Math.max(-28, adjusted));
    }
}
