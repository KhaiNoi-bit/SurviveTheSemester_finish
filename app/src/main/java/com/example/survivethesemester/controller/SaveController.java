package com.example.survivethesemester.controller;

import android.content.Context;

import com.example.survivethesemester.model.Player;
import com.example.survivethesemester.model.Semester;
import com.example.survivethesemester.model.Subject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class SaveController {
    private static final String PREF = "survive_saves";
    public static final int SLOTS = 3;

    private SaveController() {}

    public static void save(Context c, int slot, GameController g) {
        try {
            Player p = g.player; Semester s = g.semester;
            JSONObject o = new JSONObject();
            o.put("time", new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.US).format(new Date()));
            o.put("name", p.name); o.put("male", p.male); o.put("face", p.face);
            o.put("study", p.study); o.put("energy", p.energy); o.put("happy", p.happy);
            o.put("stress", p.stress); o.put("money", p.money);
            o.put("week", s.week); o.put("day", s.day); o.put("actions", s.actionsLeft);
            JSONArray a = new JSONArray();
            for (Subject sub : s.subjects) a.put(sub.readiness);
            o.put("subjects", a);
            c.getSharedPreferences(PREF, Context.MODE_PRIVATE).edit().putString("slot" + slot, o.toString()).apply();
        } catch (JSONException e) { e.printStackTrace(); }
    }

    public static boolean load(Context c, int slot, GameController g) {
        String js = c.getSharedPreferences(PREF, Context.MODE_PRIVATE).getString("slot" + slot, null);
        if (js == null) return false;
        try {
            JSONObject o = new JSONObject(js);
            Player p = new Player();
            p.name = o.getString("name"); p.male = o.getBoolean("male"); p.face = o.getInt("face");
            p.study = o.getInt("study"); p.energy = o.getInt("energy"); p.happy = o.getInt("happy");
            p.stress = o.getInt("stress"); p.money = o.getInt("money");
            Semester s = new Semester();
            s.week = o.getInt("week"); s.day = o.getInt("day"); s.actionsLeft = o.getInt("actions");
            JSONArray a = o.getJSONArray("subjects");
            for (int i = 0; i < s.subjects.size() && i < a.length(); i++) s.subjects.get(i).readiness = a.getInt(i);
            g.player = p; g.semester = s;
            g.pending = null; g.exams = null; g.log.clear();
            g.rebuild();
            return true;
        } catch (JSONException e) { e.printStackTrace(); return false; }
    }

    /** คืน {เวลา, "Week n Day n", ชื่อ} หรือ null ถ้าช่องว่าง */
    public static String[] info(Context c, int slot) {
        String js = c.getSharedPreferences(PREF, Context.MODE_PRIVATE).getString("slot" + slot, null);
        if (js == null) return null;
        try {
            JSONObject o = new JSONObject(js);
            return new String[]{o.getString("time"),
                    "Week " + o.getInt("week") + " Day " + o.getInt("day"), o.getString("name")};
        } catch (JSONException e) { return null; }
    }

    public static void delete(Context c, int slot) {
        c.getSharedPreferences(PREF, Context.MODE_PRIVATE).edit().remove("slot" + slot).apply();
    }

    public static void deleteAll(Context c) {
        c.getSharedPreferences(PREF, Context.MODE_PRIVATE).edit().clear().apply();
    }
}
