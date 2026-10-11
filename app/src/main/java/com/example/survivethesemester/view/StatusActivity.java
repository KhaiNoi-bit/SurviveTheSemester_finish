package com.example.survivethesemester.view;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.survivethesemester.R;
import com.example.survivethesemester.controller.GameController;
import com.example.survivethesemester.model.Player;
import com.example.survivethesemester.model.Semester;
import com.example.survivethesemester.model.Subject;

/** สถานะผู้เล่น + ความพร้อมสอบรายวิชา  (layout: activity_status.xml) */
public class StatusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        GameController g = GameController.get();
        Player p = g.player;
        setContentView(R.layout.activity_status);
        Ui.insets(findViewById(R.id.root));
        Ui.header(this, "สถานะ", true);

        ((TextView) findViewById(R.id.tvName)).setText(p.faceEmoji() + "  " + p.name);
        Ui.bindStat(findViewById(R.id.rowStudy),  "📚 การเรียน",   p.study,  100, R.color.blue,   p.study + "/100");
        Ui.bindStat(findViewById(R.id.rowEnergy), "⚡ พลังงาน",    p.energy, 100, R.color.yellow, p.energy + "/100");
        Ui.bindStat(findViewById(R.id.rowHappy),  "😊 ความสุข",    p.happy,  100, R.color.green,  p.happy + "/100");
        Ui.bindStat(findViewById(R.id.rowStress), "😰 ความเครียด", p.stress, 100, R.color.red,    p.stress + "/100");
        ((TextView) findViewById(R.id.tvMoney)).setText("💰 เงิน  " + p.money + " บาท");

        LinearLayout box = findViewById(R.id.llSubjects);
        for (Subject s : g.semester.subjects) {
            View row = getLayoutInflater().inflate(R.layout.item_stat, box, false);
            int col = s.readiness >= 70 ? R.color.green : s.readiness >= 50 ? R.color.yellow : R.color.red;
            Ui.bindStat(row, s.emoji + " " + s.name, s.readiness, 100, col, s.readiness + "%", 140);
            box.addView(row);
        }
        ((TextView) findViewById(R.id.tvNote)).setText("* ต่ำกว่า 50% เสี่ยงสอบตก และความพร้อมจะลดลง "
                + Semester.WEEKLY_FORGET + " ทุกสัปดาห์");
    }
}
