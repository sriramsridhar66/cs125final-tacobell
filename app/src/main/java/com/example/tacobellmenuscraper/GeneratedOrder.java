package com.example.tacobellmenuscraper;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.HashMap;

public class GeneratedOrder extends AppCompatActivity {

    private Menu menu;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generated_order);

        Intent intent = getIntent();
        menu = new Menu(intent.getDoubleExtra("moneyAmount", 0),
                intent.getIntExtra("dollarMenuNumber", 0), intent.getBooleanExtra("drinks", false));
        try {
            menu.loadMenu();
        } catch (IOException e) {
            e.printStackTrace();
        }

        loadUI();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void loadUI() {
        menu.sortMap();
        addToTable(menu.getFreeItems());
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void addToTable(HashMap<String, Double> hashMap) {
        LinearLayout everythingLinear = findViewById(R.id.everythingLinear);

        for (HashMap.Entry<String, Double> entry : hashMap.entrySet()) {
            LinearLayout itemLinear = new LinearLayout(this);
            itemLinear.setOrientation(LinearLayout.VERTICAL);

            LinearLayout innerItemLinear = new LinearLayout(this);
            innerItemLinear.setOrientation(LinearLayout.HORIZONTAL);

            TextView item = new TextView(this);
            item.setText(entry.getKey());
            item.setGravity(1);

            TextView price = new TextView(this);
            price.setText("$" + entry.getValue());
            price.setGravity(0);

            innerItemLinear.addView(item);
            innerItemLinear.addView(price);
            itemLinear.addView(innerItemLinear);
            everythingLinear.addView(itemLinear);


        }
    }

}
