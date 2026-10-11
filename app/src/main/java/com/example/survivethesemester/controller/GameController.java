package com.example.survivethesemester.controller;

import com.example.survivethesemester.model.Action;
import com.example.survivethesemester.model.Event;
import com.example.survivethesemester.model.Exam;
import com.example.survivethesemester.model.Player;
import com.example.survivethesemester.model.Semester;
import com.example.survivethesemester.model.Subject;

import java.util.ArrayList;
import java.util.List;

/** ตัวกลางเก็บสถานะเกมทั้งหมด (Singleton) */
public class GameController {
    private static GameController instance;
    public static GameController get() {
        if (instance == null) instance = new GameController();
        return instance;
    }

    public Player player;
    public Semester semester;
    public Event pending;            // event ที่รอให้ผู้เล่นตัดสินใจ
    public List<Exam> exams;         // ผลสอบ (null = ยังไม่สอบ)
    public char ending = 'B';
    public final List<String> log = new ArrayList<>();

    public PlayerController playerCtl;
    public ActivityController activityCtl;
    public EventController eventCtl;
    public ExamController examCtl;
    public ShopController shopCtl;

    public void newGame(String name, boolean male, int face) {
        player = new Player();
        player.name = name; player.male = male; player.face = face;
        semester = new Semester();
        pending = null; exams = null; log.clear();
        rebuild();
    }

    /** สร้าง controller ใหม่ (เรียกหลัง newGame / โหลดเซฟ) */
    public void rebuild() {
        playerCtl   = new PlayerController(player);
        activityCtl = new ActivityController(player);
        eventCtl    = new EventController();
        examCtl     = new ExamController();
        shopCtl     = new ShopController(player);
    }

    public boolean hasGame() { return player != null && semester != null; }

    public String doAction(Action a, Subject sub) {
        String r = activityCtl.perform(a, sub);
        addLog("W" + semester.week + "D" + semester.day + "  " + r);

        boolean newDay = semester.useAction();
        if (!semester.isOver()) {
            if (newDay && eventCtl.chance(30))      pending = eventCtl.assignmentEvent(semester);
            else if (eventCtl.chance(35))           pending = eventCtl.randomEvent();
        }
        return r;
    }

    public String resolveEvent(Event.Option o) {
        Event e = pending;
        String extra = eventCtl.resolve(e, o, player, semester);
        addLog(e.emoji + " " + e.title + " → " + o.label);
        pending = null;
        for (int i = 0; i < o.actionCost; i++) semester.useAction();
        return extra;
    }

    public void ensureExam() {
        if (exams == null) {
            exams = examCtl.takeExam(player, semester);
            ending = examCtl.decideEnding(exams, player);
        }
    }

    private void addLog(String s) {
        log.add(0, s);
        while (log.size() > 20) log.remove(log.size() - 1);
    }
}
