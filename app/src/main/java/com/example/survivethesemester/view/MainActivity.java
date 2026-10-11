package com.example.survivethesemester.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.survivethesemester.R;
import com.example.survivethesemester.controller.SaveController;

/** 1. หน้าเมนูหลัก  (layout: activity_main.xml) */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_main);
        Ui.insets(findViewById(R.id.root));

        findViewById(R.id.btnNewGame).setOnClickListener(
                v -> startActivity(new Intent(this, CreateCharacterActivity.class)));

        findViewById(R.id.btnLoad).setOnClickListener(v -> {
            Intent i = new Intent(this, SaveLoadActivity.class);
            i.putExtra("mode", "load");
            startActivity(i);
        });

        findViewById(R.id.btnHowTo).setOnClickListener(v -> showHowTo());
        findViewById(R.id.btnSettings).setOnClickListener(v -> showSettings());
    }

    private void showHowTo() {
        new AlertDialog.Builder(this)
                .setTitle("วิธีการเล่น")
                .setMessage("• คุณคือนักศึกษา ต้องเอาตัวรอดตั้งแต่เปิดเทอมจนถึงวันสอบปลายภาค (4 สัปดาห์)\n"
                        + "• แต่ละวันมี 3 Actions เลือกกิจกรรมให้สมดุลจึงไม่พังทั้งเรียนและสุขภาพ\n"
                        + "• ดูแล 5 ค่า: 📚 การเรียน ⚡ พลังงาน 😊 ความสุข 😰 ความเครียด 💰 เงิน\n"
                        + "• ระวัง Event สุ่มและ Assignment ที่ค่อย ๆ เพิ่มความกดดันและมีผลต่อคะแนนรายวิชา\n"
                        + "• ความพร้อมสอบของแต่ละวิชาจะลดลงทุกสัปดาห์ ต้องเข้าเรียน/อ่านหนังสือและจัดการแรงกายใจ\n"
                        + "• ทำงานให้สมดุลและสู้ไปถึง Ending A")
                .setPositiveButton("เข้าใจแล้ว", null).show();
    }

    private void showSettings() {
        new AlertDialog.Builder(this)
                .setTitle("ตั้งค่า")
                .setItems(new String[]{"🗑️ ลบข้อมูลเซฟทั้งหมด"}, (d, i) -> {
                    SaveController.deleteAll(this);
                    Ui.toast(this, "ลบข้อมูลเซฟแล้ว");
                })
                .setNegativeButton("ปิด", null).show();
    }
}
