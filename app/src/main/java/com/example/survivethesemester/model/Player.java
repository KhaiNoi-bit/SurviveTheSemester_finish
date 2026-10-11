package com.example.survivethesemester.model;

public class Player {
    public String name = "";
    public boolean male = true;
    public int face = 0;

    // ค่าสถานะเริ่มต้นตามเกมที่ต้องการ: เริ่มด้วยความกดดันน้อย แต่มีพลังงานและเงินเพียงพอ
    public int study = 0, energy = 50, happy = 0, stress = 0, money = 500;

    public void apply(int dStudy, int dEnergy, int dHappy, int dStress, int dMoney) {
        study = clamp(study + dStudy);
        energy = clamp(energy + dEnergy);
        happy = clamp(happy + dHappy);
        stress = clamp(stress + dStress);
        money = Math.max(0, money + dMoney);
    }

    private static int clamp(int v) { return Math.max(0, Math.min(100, v)); }

    public boolean isExhausted()      { return energy <= 5; }
    public boolean isStressOverload() { return stress >= 100; }
    public boolean isBroke()          { return money <= 0; }

    public static String faceEmoji(boolean male, int face) {
        String[] m = {"🧑‍🎓", "👦", "🧑", "🧒"};
        String[] f = {"👩‍🎓", "👧", "👩", "🧒"};
        return (male ? m : f)[Math.abs(face) % 4];
    }

    public String faceEmoji() { return faceEmoji(male, face); }
}
