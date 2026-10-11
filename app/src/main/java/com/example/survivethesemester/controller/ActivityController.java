package com.example.survivethesemester.controller;

import com.example.survivethesemester.model.Action;
import com.example.survivethesemester.model.Player;
import com.example.survivethesemester.model.Subject;

public class ActivityController {
    private final Player p;

    public ActivityController(Player p) { this.p = p; }

    /** คืนเหตุผลถ้ากิจกรรมถูกล็อก, null ถ้าทำได้ */
    public String blockReason(Action a) {
        if (a.needsEnergy && p.isExhausted()) return "หมดแรง! ต้องนอนหรือพักก่อน";
        if (a.money < 0 && p.money < -a.money) return "เงินไม่พอ";
        return null;
    }

    public String perform(Action a, Subject sub) {
        boolean overload = p.isStressOverload();
        int ds = a.study;
        if (overload && ds > 0) ds = ds / 2;               // เครียดเกิน -> เรียนได้น้อยลง
        p.apply(ds, a.energy, a.happy, a.stress, a.money);
        if (sub != null && a.subjectGain > 0) sub.add(overload ? a.subjectGain / 2 : a.subjectGain);
        return a.emoji + " " + a.name + (sub != null ? " (" + sub.name + ")" : "");
    }
}
