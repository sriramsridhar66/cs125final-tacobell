package com.example.tacobellmenuscraper;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;

public class GeneratedOrder extends AppCompatActivity {

    private Menu menu;

    private double total;

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
        addToTable(menu.getComboDrinks());
        addToTable(menu.getDollarMenu());
        addToTable(menu.getRegularItems());
        //addToTable(menu.getMenuMap()); //remove once proper logic is created

        TextView totalPrice = findViewById(R.id.totalPrice);
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        totalPrice.setText("$" + decimalFormat.format(total));

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void addToTable(HashMap<String, Double> hashMap) {
        LinearLayout orderLayout = findViewById(R.id.orderLayout);

        DecimalFormat decimalFormat = new DecimalFormat("#.00");

        for (HashMap.Entry<String, Double> entry : hashMap.entrySet()) {
            LinearLayout itemLinear = new LinearLayout(this);
            itemLinear.setOrientation(LinearLayout.HORIZONTAL);
            itemLinear.setPadding(0, 10, 5, 10);

            TextView item = new TextView(this);
            item.setText(entry.getKey());
            item.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,1));
            item.setTextSize(25);

            TextView price = new TextView(this);
            price.setText("$" + decimalFormat.format(entry.getValue()));
            price.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,0));
            price.setTextSize(25);
            total += entry.getValue();

            itemLinear.addView(item);
            itemLinear.addView(price);
            orderLayout.addView(itemLinear);
        }
    }

}