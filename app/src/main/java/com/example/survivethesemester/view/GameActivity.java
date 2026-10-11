package com.example.survivethesemester.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.survivethesemester.R;
import com.example.survivethesemester.controller.GameController;
import com.example.survivethesemester.model.Player;
import com.example.survivethesemester.model.Semester;

/** 3. หน้าหลักในเกม  (layout: activity_game.xml) */
public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_game);
        Ui.insets(findViewById(R.id.root));

        for (int id : new int[]{R.id.navActivity, R.id.navStatus, R.id.navEvent, R.id.navShop, R.id.navSave}) {
            Ui.bigFirstLine(findViewById(id));
        }

        findViewById(R.id.navActivity).setOnClickListener(
                v -> startActivity(new Intent(this, ActivityActivity.class)));
        findViewById(R.id.navStatus).setOnClickListener(
                v -> startActivity(new Intent(this, StatusActivity.class)));
        findViewById(R.id.navEvent).setOnClickListener(v -> showLog());
        findViewById(R.id.navShop).setOnClickListener(
                v -> startActivity(new Intent(this, ShopActivity.class)));
        findViewById(R.id.navSave).setOnClickListener(v -> {
            Intent i = new Intent(this, SaveLoadActivity.class);
            i.putExtra("mode", "save");
            startActivity(i);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        GameController g = GameController.get();
        if (!g.hasGame()) {                       // process ถูกปิด -> กลับเมนู
            Intent i = new Intent(this, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
            return;
        }
        if (g.semester.isOver()) { startActivity(new Intent(this, ExamActivity.class)); return; }
        if (g.pending != null)   { startActivity(new Intent(this, EventActivity.class)); return; }
        refresh(g);
    }

    private void refresh(GameController g) {
        Player p = g.player;
        Semester s = g.semester;
        Ui.header(this, "WEEK " + s.week + "  ·  DAY " + s.day, false);
        Ui.headerRight(this, "🪙 " + String.format(java.util.Locale.US, "%,d", p.money));

        ((TextView) findViewById(R.id.tvDay)).setText(s.dayName() + "   08:00 ─── 22:00");
        ((TextView) findViewById(R.id.tvFace)).setText(p.faceEmoji());
        ((TextView) findViewById(R.id.tvBubble)).setText(g.playerCtl.statusMessage());
        ((TextView) findViewById(R.id.tvActions)).setText("⏳ วันนี้เหลือ " + s.actionsLeft + "/"
                + Semester.MAX_ACTIONS + " Actions   ·   สอบปลายภาคอีก " + s.daysLeft() + " วัน");

        // สถานะวิกฤต
        LinearLayout warn = findViewById(R.id.llWarnings);
        warn.removeAllViews();
        for (String w : g.playerCtl.warnings()) {
            TextView t = (TextView) LayoutInflater.from(this).inflate(R.layout.item_warning, warn, false);
            t.setText(w);
            warn.addView(t);
        }

        // ค่าสถานะ
        Ui.bindStat(findViewById(R.id.rowStudy),  "📚 การเรียน",   p.study,  100, R.color.blue,   p.study + "/100");
        Ui.bindStat(findViewById(R.id.rowEnergy), "⚡ พลังงาน",    p.energy, 100, R.color.yellow, p.energy + "/100");
        Ui.bindStat(findViewById(R.id.rowHappy),  "😊 ความสุข",    p.happy,  100, R.color.green,  p.happy + "/100");
        Ui.bindStat(findViewById(R.id.rowStress), "😰 ความเครียด", p.stress, 100, R.color.red,    p.stress + "/100");
        Ui.bindStat(findViewById(R.id.rowMoney),  "💰 เงิน",       Math.min(p.money, 5000), 5000, R.color.orange, String.valueOf(p.money));
    }

    private void showLog() {
        GameController g = GameController.get();
        String msg = g.log.isEmpty() ? "ยังไม่มีเหตุการณ์" : android.text.TextUtils.join("\n", g.log);
        new AlertDialog.Builder(this).setTitle("บันทึกเหตุการณ์ล่าสุด")
                .setMessage(msg).setPositiveButton("ปิด", null).show();
    }
}
