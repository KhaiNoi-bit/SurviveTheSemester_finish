package com.example.survivethesemester.view;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.survivethesemester.R;
import com.example.survivethesemester.controller.GameController;
import com.example.survivethesemester.model.Event;

/**
 * 5. เหตุการณ์พิเศษ  (layout: activity_event.xml + item_option.xml)
 * พื้นหลังเปลี่ยนตามฉากของ event (Event.scene) — การ์ดสีขาวอยู่ในภาพ เราวางข้อความทับตรงกรอบ
 */
public class EventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        GameController g = GameController.get();
        Event e = g.pending;
        if (e == null) { finish(); return; }

        setContentView(R.layout.activity_event);
        Ui.insets(findViewById(R.id.root));
        applyScene(e.scene);

        ((TextView) findViewById(R.id.tvTitle)).setText(e.emoji + " " + e.title);
        ((TextView) findViewById(R.id.tvDesc)).setText(e.description + "\n\nคุณควรทำอย่างไร?");

        int[] bgs = {R.drawable.bg_btn_blue, R.drawable.bg_btn_green, R.drawable.bg_btn_red};
        LinearLayout box = findViewById(R.id.llOptions);
        for (int i = 0; i < e.options.size(); i++) {
            Event.Option o = e.options.get(i);
            TextView btn = (TextView) getLayoutInflater().inflate(R.layout.item_option, box, false);
            btn.setText(o.label + (o.hint == null || o.hint.isEmpty() ? "" : "\n" + o.hint));
            btn.setTextSize(15);
            btn.setPadding(Ui.dp(this, 12), Ui.dp(this, 9), Ui.dp(this, 12), Ui.dp(this, 11));
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

    /** เลือกภาพพื้นหลัง + สีรอบภาพ (ใต้แถบสถานะ / ใต้ภาพเมื่อจอยาว) ตามฉาก */
    private void applyScene(String scene) {
        int img, top, bottom;
        switch (scene == null ? "" : scene) {
            case "rain":  img = R.drawable.ev_rain;  top = Color.parseColor("#1C434A"); bottom = Color.parseColor("#2B4550"); break;
            case "shabu": img = R.drawable.ev_shabu; top = Color.parseColor("#5D4E47"); bottom = Color.parseColor("#5A4738"); break;
            case "club":  img = R.drawable.ev_club;  top = Color.parseColor("#9CA695"); bottom = Color.parseColor("#A09591"); break;
            case "nonet": img = R.drawable.ev_nonet; top = Color.parseColor("#10264E"); bottom = Color.parseColor("#0B1222"); break;
            default:      img = R.drawable.ev_group; top = Color.parseColor("#54596B"); bottom = Color.parseColor("#544033"); break;
        }
        ((ImageView) findViewById(R.id.ivBg)).setImageResource(img);
        findViewById(R.id.root).setBackground(new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM, new int[]{top, bottom}));
        getOnBackPressedDispatcher().addCallback(this, new androidx.activity.OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                /* ต้องเลือกตัวเลือกก่อน */
            }
        });
    }
}
