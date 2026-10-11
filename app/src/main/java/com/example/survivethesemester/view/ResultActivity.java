package com.example.survivethesemester.view;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.survivethesemester.R;
import com.example.survivethesemester.controller.GameController;
import com.example.survivethesemester.model.Player;

/** 7 / 10. ผลลัพธ์ Ending A–E  (layout: activity_result.xml) */
public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        GameController g = GameController.get();
        if (!g.hasGame() || g.exams == null) { finish(); return; }
        Player p = g.player;
        char e = g.ending;

        String emoji, title, desc, quote;
        int color;
        switch (e) {
            case 'A': emoji = "🥇"; title = "นักศึกษาตัวอย่าง"; color = R.color.yellow;
                desc = "คุณสามารถรักษาสมดุลของชีวิตได้ยอดเยี่ยม\nเรียนดี มีความสุข และมีอนาคตที่สดใส";
                quote = "\"ความพยายามไม่เคยทรยศใคร\""; break;
            case 'B': emoji = "🎓"; title = "เรียนจบแบบเฉียดฉิว"; color = R.color.blue;
                desc = "สอบผ่าน แต่ชีวิตช่วงเทอมแทบพัง";
                quote = "\"รอดแล้ว...แต่แทบไม่เหลืออะไร\""; break;
            case 'C': emoji = "💰"; title = "Working Student"; color = R.color.green;
                desc = "เรียนพอใช้ แต่หาเงินได้เยอะมาก";
                quote = "\"เงินมา ชีวิตก็ไปต่อได้\""; break;
            case 'D': emoji = "🎮"; title = "เกมเมอร์"; color = R.color.purple;
                desc = "ความสุขสูงมาก 😂\nแต่คะแนนเรียนเกือบไม่รอด";
                quote = "\"อีกตาเดียว...จริงๆ นะ\""; break;
            default:  emoji = "😵"; title = "Academic Disaster"; color = R.color.gray;
                desc = "คุณพยายามแล้ว...แต่สุดท้ายก็สอบไม่ผ่านบางวิชา\nแต่ไม่เป็นไร อย่างน้อยคุณก็ได้เรียนรู้และสู้ใหม่ครั้งหน้า!";
                quote = "\"ล้มได้ ลุกใหม่ได้\""; break;
        }

        setContentView(R.layout.activity_result);
        Ui.insets(findViewById(R.id.root));
        Ui.header(this, "ผลลัพธ์", false);

        ((TextView) findViewById(R.id.tvEmoji)).setText(emoji);
        TextView badge = findViewById(R.id.tvBadge);
        badge.setText("ENDING " + e);
        badge.setBackgroundTintList(ColorStateList.valueOf(Ui.color(this, color)));
        ((TextView) findViewById(R.id.tvTitle)).setText(title);
        ((TextView) findViewById(R.id.tvDesc)).setText(desc);
        ((TextView) findViewById(R.id.tvQuote)).setText(quote);

        Ui.bindStat(findViewById(R.id.rowStudy),  "📚 การเรียน",   p.study,  100, R.color.blue,   String.valueOf(p.study));
        Ui.bindStat(findViewById(R.id.rowEnergy), "⚡ พลังงาน",    p.energy, 100, R.color.yellow, String.valueOf(p.energy));
        Ui.bindStat(findViewById(R.id.rowHappy),  "😊 ความสุข",    p.happy,  100, R.color.green,  String.valueOf(p.happy));
        Ui.bindStat(findViewById(R.id.rowStress), "😰 ความเครียด", p.stress, 100, R.color.red,    String.valueOf(p.stress));
        ((TextView) findViewById(R.id.tvMoney)).setText("💰 เงินคงเหลือ  " + p.money + " บาท");
        ((TextView) findViewById(R.id.tvAvg)).setText("คะแนนสอบเฉลี่ย  "
                + Math.round(g.examCtl.average(g.exams)) + "/100");

        findViewById(R.id.btnMenu).setOnClickListener(v -> {
            Intent i = new Intent(this, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
        });
    }

    @Override
    public void onBackPressed() { /* ใช้ปุ่มกลับเมนูแทน */ }
}
