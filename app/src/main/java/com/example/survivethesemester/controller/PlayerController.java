package com.example.survivethesemester.controller;

import com.example.survivethesemester.model.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerController {
    private final Player p;

    public PlayerController(Player p) { this.p = p; }

    /** ข้อความในกล่องคำพูดของตัวละคร */
    public String statusMessage() {
        if (p.isExhausted())       return "หมดแรงแล้ว... ต้องไปนอนก่อน";
        if (p.isStressOverload())  return "เครียดสุดๆ แล้ว ต้องพักหน่อย!";
        if (p.isBroke())           return "ไม่มีเงินแล้ว... หางานทำดีกว่า";
        if (p.energy < 25)         return "ง่วงจัง... พักบ้างดีไหม";
        if (p.stress >= 70)        return "เริ่มเครียดแล้วนะ...";
        return "วันนี้จะเป็นวันที่ดี...สู้!";
    }

    /** สถานะวิกฤต */
    public List<String> warnings() {
        List<String> l = new ArrayList<>();
        if (p.isExhausted())      l.add("⚡ หมดแรง — เข้าเรียน/อ่านหนังสือ/ทำงานถูกล็อก ต้องนอน");
        if (p.isStressOverload()) l.add("😰 Stress Overload — ประสิทธิภาพการเรียนลดลงครึ่งหนึ่ง");
        if (p.isBroke())          l.add("💰 ไม่มีเงิน — เลือกกิจกรรมที่ไม่เสียเงิน หรือหางานทำ");
        return l;
    }
}
