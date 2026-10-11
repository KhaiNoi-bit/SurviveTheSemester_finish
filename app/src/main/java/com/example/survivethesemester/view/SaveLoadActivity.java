package com.example.survivethesemester.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.survivethesemester.R;
import com.example.survivethesemester.controller.GameController;
import com.example.survivethesemester.controller.SaveController;

/** 9. บันทึกเกม  (layout: activity_save_load.xml + item_slot.xml) */
public class SaveLoadActivity extends AppCompatActivity {
    private final GameController g = GameController.get();
    private int selected = 1;
    private LinearLayout llSlots;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_save_load);
        Ui.insets(findViewById(R.id.root));
        Ui.header(this, "บันทึกเกม", true);
        llSlots = findViewById(R.id.llSlots);

        findViewById(R.id.btnSave).setOnClickListener(v -> {
            if (!g.hasGame()) { Ui.toast(this, "ยังไม่มีเกมที่กำลังเล่น"); return; }
            SaveController.save(this, selected, g);
            Ui.toast(this, "บันทึกที่ช่อง " + selected + " แล้ว");
            buildSlots();
        });

        findViewById(R.id.btnLoadGame).setOnClickListener(v -> {
            if (SaveController.info(this, selected) == null) { Ui.toast(this, "ช่องนี้ว่าง"); return; }
            if (SaveController.load(this, selected, g)) {
                Intent i = new Intent(this, GameActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);
                finish();
            } else Ui.toast(this, "โหลดไม่สำเร็จ");
        });
        buildSlots();
    }

    private void buildSlots() {
        llSlots.removeAllViews();
        for (int s = 1; s <= SaveController.SLOTS; s++) {
            final int slot = s;
            String[] info = SaveController.info(this, slot);
            View row = getLayoutInflater().inflate(R.layout.item_slot, llSlots, false);

            ((TextView) row.findViewById(R.id.tvSlot)).setText("ช่อง " + slot);
            TextView tvInfo = row.findViewById(R.id.tvSlotInfo);
            TextView tvDay = row.findViewById(R.id.tvSlotDay);
            View del = row.findViewById(R.id.tvDelete);
            if (info == null) {
                tvInfo.setText("-- ว่าง --");
                tvDay.setVisibility(View.GONE);
                del.setVisibility(View.GONE);
            } else {
                tvInfo.setText(info[2] + "  ·  " + info[0]);
                tvDay.setText(info[1]);
                del.setOnClickListener(v -> { SaveController.delete(this, slot); buildSlots(); });
            }
            row.setBackgroundResource(slot == selected ? R.drawable.bg_card_selected : R.drawable.bg_card);
            row.setOnClickListener(v -> { selected = slot; buildSlots(); });
            llSlots.addView(row);
        }
    }
}
