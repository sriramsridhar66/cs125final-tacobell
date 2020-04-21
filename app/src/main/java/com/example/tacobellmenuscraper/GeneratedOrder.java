package com.example.tacobellmenuscraper;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class GeneratedOrder extends AppCompatActivity {

    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generated_order);

        Intent intent = getIntent();
        menu = new Menu(intent.getDoubleExtra("moneyAmount", 0),
                intent.getIntExtra("dollarMenuNumber", 0), intent.getBooleanExtra("drinks", false));
    }
}
