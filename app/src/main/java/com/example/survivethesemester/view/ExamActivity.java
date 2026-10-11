package com.example.survivethesemester.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.survivethesemester.R;
import com.example.survivethesemester.controller.GameController;
import com.example.survivethesemester.model.Exam;

/** 6. หน้าสอบปลายภาค  (layout: activity_exam.xml + item_exam.xml) */
public class ExamActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        GameController g = GameController.get();
        if (!g.hasGame()) { finish(); return; }
        g.ensureExam();

        setContentView(R.layout.activity_exam);
        Ui.insets(findViewById(R.id.root));
        Ui.header(this, "Final Exam", false);

        LinearLayout box = findViewById(R.id.llExam);
        for (Exam e : g.exams) {
            View row = getLayoutInflater().inflate(R.layout.item_exam, box, false);
            ((TextView) row.findViewById(R.id.tvExamName)).setText(e.emoji + "  " + e.subject);
            TextView sc = row.findViewById(R.id.tvExamScore);
            sc.setText(e.score + "/100");
            sc.setTextColor(Ui.color(this, e.passed() ? R.color.green : R.color.red));
            ((TextView) row.findViewById(R.id.tvExamMark)).setText(e.passed() ? "✅" : "❌");
            box.addView(row);
        }

        findViewById(R.id.btnResult).setOnClickListener(v -> {
            startActivity(new Intent(this, ResultActivity.class));
            finish();
        });
    }

    @Override
    public void onBackPressed() { /* ห้ามย้อนกลับก่อนดูผล */ }
}
