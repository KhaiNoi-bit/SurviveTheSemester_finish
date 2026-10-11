package com.example.survivethesemester.view;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.survivethesemester.R;
import com.example.survivethesemester.controller.GameController;
import com.example.survivethesemester.model.Event;

/** 5. เหตุการณ์พิเศษ  (layout: activity_event.xml + item_option.xml) */
public class EventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        GameController g = GameController.get();
        Event e = g.pending;
        if (e == null) { finish(); return; }

        setContentView(R.layout.activity_event);
        Ui.insets(findViewById(R.id.root));
        Ui.header(this, "เหตุการณ์พิเศษ", false);

        ((TextView) findViewById(R.id.tvIcon)).setText(e.emoji);
        ((TextView) findViewById(R.id.tvTitle)).setText(e.title);
        ((TextView) findViewById(R.id.tvDesc)).setText(e.description + "\n\nคุณควรทำอย่างไร?");

        int[] bgs = {R.drawable.bg_btn_blue, R.drawable.bg_btn_green, R.drawable.bg_btn_red};
        LinearLayout box = findViewById(R.id.llOptions);
        for (int i = 0; i < e.options.size(); i++) {
            Event.Option o = e.options.get(i);
            TextView btn = (TextView) getLayoutInflater().inflate(R.layout.item_option, box, false);
            btn.setText(o.label + (o.hint == null || o.hint.isEmpty() ? "" : "\n" + o.hint));
            btn.setBackgroundResource(bgs[i % 3]);
            btn.setOnClickListener(v -> {
                if (o.money < 0 && g.player.money < -o.money) { Ui.toast(this, "เงินไม่พอ"); return; }
                String extra = g.resolveEvent(o);
                if (!extra.isEmpty()) Ui.toast(this, extra);
                finish();
            });
            box.addView(btn);
        }
    }

    @Override
    public void onBackPressed() { /* ต้องเลือกตัวเลือกก่อน */ }
}
