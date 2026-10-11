package com.example.survivethesemester.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.survivethesemester.R;

/** ตัวช่วยเล็ก ๆ สำหรับ layout XML (header, แถบสถานะ, สีผลกระทบ) */
public final class Ui {
    private Ui() {}

    public static int dp(Context c, float v) {
        return Math.round(v * c.getResources().getDisplayMetrics().density);
    }

    /** รูปหน้าตัวละคร 4 แบบ (face = 0..3) */
    public static int faceRes(int face) {
        int[] r = {R.drawable.img_face0, R.drawable.img_face1, R.drawable.img_face2, R.drawable.img_face3};
        return r[Math.abs(face) % r.length];
    }

    public static int color(Context c, int colorRes) {
        return ContextCompat.getColor(c, colorRes);
    }

    public static void toast(Context c, String s) {
        Toast.makeText(c, s, Toast.LENGTH_SHORT).show();
    }

    /** กันเนื้อหาไม่ให้ชนแถบสถานะ/แถบนำทาง */
    public static void insets(View v) {
        ViewCompat.setOnApplyWindowInsetsListener(v, (view, ins) -> {
            Insets i = ins.getInsets(WindowInsetsCompat.Type.systemBars());
            view.setPadding(i.left, i.top, i.right, i.bottom);
            return WindowInsetsCompat.CONSUMED;
        });
    }

    /** ตั้งค่า layout_header.xml */
    public static void header(AppCompatActivity a, String title, boolean back) {
        View h = a.findViewById(R.id.layoutHeader);
        ((TextView) h.findViewById(R.id.tvHeaderTitle)).setText(title);
        View b = h.findViewById(R.id.tvBack);
        View sp = h.findViewById(R.id.headerSpacer);
        b.setVisibility(back ? View.VISIBLE : View.GONE);
        sp.setVisibility(back ? View.VISIBLE : View.GONE);
        b.setOnClickListener(v -> a.finish());
    }

    /** ป้ายเหรียญมุมขวาของ header เช่น "🪙 1,000" */
    public static void headerRight(AppCompatActivity a, String text) {
        View h = a.findViewById(R.id.layoutHeader);
        TextView r = h.findViewById(R.id.tvHeaderRight);
        r.setText(text);
        r.setVisibility(View.VISIBLE);
        h.findViewById(R.id.headerSpacer).setVisibility(View.GONE);
        TextView t = h.findViewById(R.id.tvHeaderTitle);
        if (h.findViewById(R.id.tvBack).getVisibility() != View.VISIBLE) {
            t.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
        }
    }

    /** ขยายบรรทัดแรก (ไอคอน emoji) ของปุ่มเมนู เช่น "📅\nกิจกรรม" */
    public static void bigFirstLine(TextView t) {
        String s = t.getText().toString();
        int n = s.indexOf('\n');
        if (n <= 0) return;
        SpannableString sp = new SpannableString(s);
        sp.setSpan(new RelativeSizeSpan(2f), 0, n, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        t.setText(sp);
    }

    /** ใส่ข้อมูลลงแถวสถานะ (item_stat.xml) */
    public static void bindStat(View row, String label, int value, int max, int colorRes,
                                String valueText, int labelWidthDp) {
        Context c = row.getContext();
        TextView l = row.findViewById(R.id.tvStatLabel);
        l.setText(label);
        if (labelWidthDp > 0) l.getLayoutParams().width = dp(c, labelWidthDp);
        ProgressBar pb = row.findViewById(R.id.pbStat);
        pb.setMax(max);
        pb.setProgress(Math.max(0, Math.min(value, max)));
        pb.setProgressTintList(ColorStateList.valueOf(color(c, colorRes)));
        ((TextView) row.findViewById(R.id.tvStatValue)).setText(valueText);
    }

    public static void bindStat(View row, String label, int value, int max, int colorRes, String valueText) {
        bindStat(row, label, value, max, colorRes, valueText, 0);
    }

    /** ข้อความผลกระทบ เช่น 📚+10 ⚡-20 (เขียว=ดี แดง=ไม่ดี) */
    public static CharSequence effects(Context c, int study, int energy, int happy, int stress, int money) {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        add(c, sb, "📚", study, false);
        add(c, sb, "⚡", energy, false);
        add(c, sb, "😊", happy, false);
        add(c, sb, "😰", stress, true);
        add(c, sb, "💰", money, false);
        return sb;
    }

    private static void add(Context c, SpannableStringBuilder sb, String icon, int v, boolean invert) {
        if (v == 0) return;
        boolean good = invert ? v < 0 : v > 0;
        int start = sb.length();
        sb.append(icon).append(v > 0 ? "+" : "").append(String.valueOf(v)).append("  ");
        sb.setSpan(new ForegroundColorSpan(color(c, good ? R.color.green : R.color.red)),
                start, sb.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }
}
