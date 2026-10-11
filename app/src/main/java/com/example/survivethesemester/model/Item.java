package com.example.survivethesemester.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Item {
    public final String emoji, name;
    public final int price, study, energy, happy, stress;

    public Item(String emoji, String name, int price, int study, int energy, int happy, int stress) {
        this.emoji = emoji; this.name = name; this.price = price;
        this.study = study; this.energy = energy; this.happy = happy; this.stress = stress;
    }

    public static List<Item> defaults() {
        List<Item> l = new ArrayList<>();
        l.add(new Item("☕", "กาแฟ", 35, 0, 14, 4, -3));
        l.add(new Item("🥗", "สลัดเพื่อสุขภาพ", 90, 4, 10, 8, -2));
        l.add(new Item("🍜", "ข้าวกล่อง", 65, 0, 18, 5, 0));
        l.add(new Item("⚡", "เครื่องดื่มชาร์จ", 80, 0, 20, 2, -4));
        l.add(new Item("📚", "สรุปบทเรียน", 180, 12, 0, 4, 0));
        l.add(new Item("🎧", "หูฟังยี่ห้อดี", 240, 0, 0, 12, -2));
        l.add(new Item("🧴", "ครีมบำรุงผิว", 190, 0, 8, 10, -2));
        l.add(new Item("📖", "หนังสืออ้างอิง", 260, 16, 0, 2, 0));
        l.add(new Item("🧘", "สมุดพักสมอง", 160, 0, 6, 14, -8));
        l.add(new Item("🧢", "หมวกกันแดด", 140, 0, 0, 8, -1));
        return l;
    }

    public static List<Item> randomPool() {
        List<Item> all = defaults();
        Collections.shuffle(all, new Random());
        int count = Math.min(6, all.size());
        return new ArrayList<>(all.subList(0, count));
    }
}
