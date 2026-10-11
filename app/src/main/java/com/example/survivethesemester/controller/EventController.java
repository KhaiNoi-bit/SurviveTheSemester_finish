package com.example.survivethesemester.controller;

import com.example.survivethesemester.model.Event;
import com.example.survivethesemester.model.Player;
import com.example.survivethesemester.model.Semester;

import java.util.Random;

public class EventController {
    private final Random rnd = new Random();

    public boolean chance(int percent) { return rnd.nextInt(100) < percent; }

    public Event randomEvent() { return Event.random(rnd); }

    public Event assignmentEvent(Semester s) {
        int i = rnd.nextInt(s.subjects.size());
        return Event.assignment(i, s.subjects.get(i).name);
    }

    /** ใช้ผลของตัวเลือก คืนข้อความพิเศษ (ถ้ามี) */
    public String resolve(Event e, Event.Option o, Player p, Semester s) {
        p.apply(o.study, o.energy, o.happy, o.stress, o.money);
        if (e.subjectIndex >= 0) s.subjects.get(e.subjectIndex).add(o.subject);

        if (o.failChance > 0 && rnd.nextInt(100) < o.failChance) {
            p.apply(o.failStudy, 0, 0, 0, 0);
            if (e.subjectIndex >= 0) s.subjects.get(e.subjectIndex).add(o.failSubject);
            return "😅 ลืมทำ Assignment! คะแนนลดลง";
        }
        return "";
    }
}
