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

        //this is for testing the generated order page so we don't have to go thru the app each time
        Button skipButton = findViewById(R.id.skipButton);
        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), GeneratedOrder.class);
                intent.putExtra("moneyAmount", 10);
                intent.putExtra("dollarMenuNumber", 2);
                intent.putExtra("drinks", true);

                startActivity(intent);
            }
        });
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
