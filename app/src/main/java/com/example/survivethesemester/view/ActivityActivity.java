package com.example.survivethesemester.view;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.survivethesemester.R;
import com.example.survivethesemester.controller.GameController;
import com.example.survivethesemester.model.Action;
import com.example.survivethesemester.model.Subject;

/** 4. เลือกกิจกรรม  (layout: activity_choose.xml + item_action.xml) */
public class ActivityActivity extends AppCompatActivity {
    private final GameController g = GameController.get();

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_choose);
        Ui.insets(findViewById(R.id.root));
        Ui.header(this, "เลือกกิจกรรม (เหลือ " + g.semester.actionsLeft + " ครั้ง)", true);

        LinearLayout list = findViewById(R.id.llList);
        for (Action a : Action.randomizedDefaults()) {
            View item = getLayoutInflater().inflate(R.layout.item_action, list, false);
            ((TextView) item.findViewById(R.id.tvIcon)).setText(a.emoji);
            ((TextView) item.findViewById(R.id.tvName)).setText(a.name);
            ((TextView) item.findViewById(R.id.tvEffects)).setText(
                    Ui.effects(this, a.study, a.energy, a.happy, a.stress, a.money));
            item.setAlpha(g.activityCtl.blockReason(a) != null ? 0.45f : 1f);

            item.setOnClickListener(v -> {
                String block = g.activityCtl.blockReason(a);
                if (block != null) { Ui.toast(this, block); return; }
                if (a.studyType) pickSubject(a); else perform(a, null);
            });
            list.addView(item);
        }
    }

    private void pickSubject(Action a) {
        String[] names = new String[g.semester.subjects.size()];
        for (int i = 0; i < names.length; i++) {
            Subject s = g.semester.subjects.get(i);
            names[i] = s.emoji + "  " + s.name + " — " + s.readiness + "%";
        }
        new AlertDialog.Builder(this).setTitle("เลือกวิชา")
                .setItems(names, (d, i) -> perform(a, g.semester.subjects.get(i)))
                .setNegativeButton("ยกเลิก", null).show();
    }

    private void perform(Action a, Subject sub) {
        Ui.toast(this, g.doAction(a, sub));
        finish();   // GameActivity.onResume จะเช็ค Event / สอบปลายภาคต่อให้
    }
}
