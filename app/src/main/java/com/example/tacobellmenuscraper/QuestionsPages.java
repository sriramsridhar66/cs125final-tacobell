package com.example.tacobellmenuscraper;

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

    private void getMoney(TextView whatToInput) {
        whatToInput.setText("Enter the amount of money you will be spending in Taco Bell today.");
        final EditText input = findViewById(R.id.optionsInput);
        Button nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp = input.getText().toString();
                double value = Double.parseDouble(temp);
                return;
            }
        });
        
    }
    private void getDollarMenu(TextView whatToInput) {
        whatToInput.setText("Enter the amount of dollar menu items you want.");
    }


}
