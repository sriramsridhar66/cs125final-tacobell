package com.example.tacobellmenuscraper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setLandingPage();
    }

    private void setLandingPage() {
        Button startButton = findViewById(R.id.startButton);
        final ImageView imageView = findViewById(R.id.logoImage);

        final ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, imageView, "tacoBellLogo");

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), QuestionsPages.class);
                startActivity(intent, options.toBundle());
            }
        });
    }
}
