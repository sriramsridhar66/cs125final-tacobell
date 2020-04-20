package com.example.tacobellmenuscraper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class QuestionsPages extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions_pages);

        EditText optionsInput = findViewById(R.id.optionsInput);
        TextView whatToInput = findViewById(R.id.whatToInput);
        getMoney(whatToInput);
    }

    private double getMoney(TextView whatToInput) {
        whatToInput.setText("Enter the amount of money you will be spending in Taco Bell today.");
        EditText input = findViewById(R.id.optionsInput);
        String temp = input.getText().toString();
        double value = Double.parseDouble(temp);
        return value;


    }
    private void getDollarMenu(TextView whatToInput) {
        whatToInput.setText("Enter the amount of dollar menu items you want.");
    }


}
