package com.example.survivethesemester.view;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.survivethesemester.R;
import com.example.survivethesemester.controller.GameController;
import com.example.survivethesemester.model.Item;

/** 8. ร้านค้า  (layout: activity_shop.xml + item_action.xml) */
public class ShopActivity extends AppCompatActivity {
    private final GameController g = GameController.get();

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_shop);
        Ui.insets(findViewById(R.id.root));
        build();
    }

    private void build() {
        Ui.header(this, "ร้านค้า", true);
        Ui.headerRight(this, "🪙 " + String.format(java.util.Locale.US, "%,d", g.player.money));
        LinearLayout list = findViewById(R.id.llList);
        list.removeAllViews();

        for (Item it : g.shopCtl.items) {
            View row = getLayoutInflater().inflate(R.layout.item_action, list, false);
            ((TextView) row.findViewById(R.id.tvIcon)).setText(it.emoji);
            ((TextView) row.findViewById(R.id.tvName)).setText(it.name);
            ((TextView) row.findViewById(R.id.tvEffects)).setText(
                    Ui.effects(this, it.study, it.energy, it.happy, it.stress, 0));
            TextView price = row.findViewById(R.id.tvPrice);
            price.setVisibility(View.VISIBLE);
            price.setText("🪙 " + it.price);
            row.setAlpha(g.player.money >= it.price ? 1f : 0.45f);

            row.setOnClickListener(v -> {
                if (g.shopCtl.buy(it)) { Ui.toast(this, "ซื้อ " + it.name + " แล้ว"); build(); }
                else Ui.toast(this, "เงินไม่พอ");
            });
            list.addView(row);
        }
    }
}
