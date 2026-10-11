package com.example.survivethesemester.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.survivethesemester.R;
import com.example.survivethesemester.controller.GameController;
import com.example.survivethesemester.model.Player;

/** 2. สร้างตัวละคร  (layout: activity_create_character.xml) */
public class CreateCharacterActivity extends AppCompatActivity {
    private boolean male = true;
    private int face = 0;

    private final int[] faceIds = {R.id.tvFace0, R.id.tvFace1, R.id.tvFace2, R.id.tvFace3};
    private TextView tvPreview, btnMale, btnFemale;
    private EditText etName;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_create_character);
        Ui.insets(findViewById(R.id.root));
        Ui.header(this, "สร้างตัวละคร", true);

        tvPreview = findViewById(R.id.tvPreview);
        btnMale = findViewById(R.id.btnMale);
        btnFemale = findViewById(R.id.btnFemale);
        etName = findViewById(R.id.etName);

        etName.setText("");
        etName.setHint("ชื่อเล่นของคุณ");

        btnMale.setOnClickListener(v -> { male = true; refresh(); });
        btnFemale.setOnClickListener(v -> { male = false; refresh(); });
        for (int i = 0; i < faceIds.length; i++) {
            final int idx = i;
            findViewById(faceIds[i]).setOnClickListener(v -> { face = idx; refresh(); });
        }

        findViewById(R.id.btnConfirm).setOnClickListener(v -> {
            String n = etName.getText().toString().trim();
            if (n.isEmpty()) { Ui.toast(this, "กรุณาใส่ชื่อเล่น"); return; }
            GameController.get().newGame(n, male, face);
            startActivity(new Intent(this, GameActivity.class));
            finish();
        });
        refresh();
    }

    private void refresh() {
        tvPreview.setText(Player.faceEmoji(male, face));
        select(btnMale, male);
        select(btnFemale, !male);
        for (int i = 0; i < faceIds.length; i++) {
            TextView t = findViewById(faceIds[i]);
            t.setText(Player.faceEmoji(male, i));
            select(t, i == face);
        }
    }

    private void select(TextView t, boolean sel) {
        t.setBackgroundResource(sel ? R.drawable.bg_chip_selected : R.drawable.bg_chip);
        t.setTextColor(sel ? Color.WHITE : Ui.color(this, R.color.text_main));
    }
}
