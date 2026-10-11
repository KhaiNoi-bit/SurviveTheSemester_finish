package com.example.survivethesemester.controller;

import com.example.survivethesemester.model.Item;
import com.example.survivethesemester.model.Player;

import java.util.List;

public class ShopController {
    private final Player p;
    public final List<Item> items = Item.randomPool();

    public ShopController(Player p) { this.p = p; }

    public boolean buy(Item it) {
        if (p.money < it.price) return false;
        p.apply(it.study, it.energy, it.happy, it.stress, -it.price);
        return true;
    }
}
