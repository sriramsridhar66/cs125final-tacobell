package com.example.tacobellmenuscraper;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.HashMap;

public class GeneratedOrder extends AppCompatActivity {

    private Menu menu;

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

    private void loadUI() {
        menu.sortMap();
        addToTable(menu.getFreeItems());
    }

    private void addToTable(HashMap<String, Double> hashMap) {
        TableLayout table = findViewById(R.id.orderTable);

        for (HashMap.Entry<String, Double> entry : hashMap.entrySet()) {
            TableRow itemRow = new TableRow(this);
            TextView item = new TextView(this);
            TextView price = new TextView(this);

            item.setText(entry.getKey());
            item.setGravity(View.TEXT_ALIGNMENT_CENTER);
            item.setTextSize(18);
            item.setTextColor(Color.BLACK);
            itemRow.addView(item);

            price.setText("$" + entry.getValue());
            price.setGravity(View.TEXT_ALIGNMENT_CENTER);
            price.setTextSize(18);
            price.setTextColor(Color.BLACK);
            itemRow.addView(price);

            table.addView(itemRow);

            /*<TextView
                android:id="@+id/tableHeaderItem"
                android:layout_width="236dp"
                android:layout_height="match_parent"
                android:fontFamily="@font/lato"
                android:gravity="center_horizontal"
                android:text="Item Name"
                android:textSize="30sp"
                android:textStyle="bold" />

            <TextView
                android:id="@+id/tableHeaderPrice"
                android:layout_width="227dp"
                android:layout_height="wrap_content"
                android:layout_gravity="center_horizontal"
                android:fontFamily="@font/lato"
                android:text="Price"
                android:textSize="30sp"
                android:textStyle="bold" />*/

        }
    }

}
