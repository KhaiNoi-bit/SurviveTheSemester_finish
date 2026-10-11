package com.example.survivethesemester.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.survivethesemester.R;
import com.example.survivethesemester.controller.GameController;

/** 2. สร้างตัวละคร  (layout: activity_create_character.xml — วางทับภาพ bg_create) */
public class CreateCharacterActivity extends AppCompatActivity {
    private int face = 0;

    private final int[] faceIds = {R.id.ivFace0, R.id.ivFace1, R.id.ivFace2, R.id.ivFace3};
    private ImageView ivPreview;
    private EditText etName;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_create_character);
        Ui.insets(findViewById(R.id.root));

        ivPreview = findViewById(R.id.tvPreview);
        etName = findViewById(R.id.etName);
        etName.setText("");

        findViewById(R.id.tvBack).setOnClickListener(v -> finish());
        for (int i = 0; i < faceIds.length; i++) {
            final int idx = i;
            ImageView iv = findViewById(faceIds[i]);
            iv.setImageResource(Ui.faceRes(i));
            iv.setOnClickListener(v -> { face = idx; refresh(); });
        }

        findViewById(R.id.btnConfirm).setOnClickListener(v -> {
            String n = etName.getText().toString().trim();
            if (n.isEmpty()) { Ui.toast(this, "กรุณาใส่ชื่อเล่น"); return; }
            GameController.get().newGame(n, true, face);   // ตัวละครมีแบบเดียว (ชาย) เลือกได้แค่หน้าตา
            startActivity(new Intent(this, GameActivity.class));
            finish();
        });
        refresh();
    }

    private void refresh() {
        ivPreview.setImageResource(Ui.faceRes(face));
        for (int i = 0; i < faceIds.length; i++) {
            findViewById(faceIds[i]).setBackgroundResource(i == face ? R.drawable.bg_face_selected : 0);
        }
    }
}
