package com.example.survivethesemester;

import com.example.survivethesemester.model.Player;
import com.example.survivethesemester.model.Semester;

import org.junit.Test;

import static org.junit.Assert.*;

public class ExampleUnitTest {
    @Test
    public void player_defaults_match_requested_balance() {
        Player player = new Player();
        assertEquals("", player.name);
        assertTrue(player.male);
        assertEquals(0, player.study);
        assertEquals(50, player.energy);
        assertEquals(0, player.happy);
        assertEquals(0, player.stress);
        assertEquals(500, player.money);
    }

    @Test
    public void semester_is_four_weeks_long() {
        assertEquals(4, Semester.TOTAL_WEEKS);
        assertEquals(1, new Semester().week);
        assertEquals(1, new Semester().day);
    }
}