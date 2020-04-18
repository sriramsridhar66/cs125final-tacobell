package com.example.tacobellmenuscraper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button loadMenuButton = findViewById(R.id.loadMenu);
        loadMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Menu menu = new Menu();
                try {
                    menu.createMenu();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
