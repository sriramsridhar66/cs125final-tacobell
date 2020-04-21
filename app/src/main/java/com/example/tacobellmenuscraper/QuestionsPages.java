package com.example.tacobellmenuscraper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

public class QuestionsPages extends AppCompatActivity {

    private EditText input;

    private TextView whatToInput;

    private TextView badInputMessage;

    private Button nextButton;

    private double moneyAmount;

    private int dollarMenuNumber;

    private boolean drinks;

    private boolean goNext = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions_pages);

        input = findViewById(R.id.optionsInput);
        whatToInput = findViewById(R.id.whatToInput);
        badInputMessage = findViewById(R.id.badInputMessage);
        badInputMessage.setVisibility(View.INVISIBLE);
        nextButton = findViewById(R.id.nextButton);
        RadioGroup radioGroup = findViewById(R.id.drinksRadioGroup);
        radioGroup.setVisibility(View.INVISIBLE);

        getMoney();
    }


    private void getMoney() {
        input.setText("");
        whatToInput.setText("How much money are you spending?");

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp = input.getText().toString();

                try {
                    double notRounded = Double.parseDouble(temp);
                    moneyAmount = Math.round(notRounded * 100.0)/100;
                    goNext = true;
                } catch (NumberFormatException nfe) {
                    badInputMessage.setVisibility(View.VISIBLE);
                    badInputMessage.setText("The amount of money must be a number. Please try again.");
                    goNext = false;
                }

                if (goNext) {
                    badInputMessage.setVisibility(View.INVISIBLE);
                    getDollarMenu();
                } else {
                    getMoney();
                }
            }
        });
    }


    private void getDollarMenu() {
        input.setText("");
        whatToInput.setText("How many dollar menu items do you want? (Up to 6)");

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp = input.getText().toString();

                try {
                    dollarMenuNumber = Integer.parseInt(temp);
                    goNext = true;
                } catch (NumberFormatException nfe) {
                    badInputMessage.setText("The number of dollar menu items must be an integer. Please try again.");
                    badInputMessage.setVisibility(View.VISIBLE);
                    goNext = false;
                }

                if (goNext && dollarMenuNumber > moneyAmount) {
                    badInputMessage.setVisibility(View.VISIBLE);
                    badInputMessage.setText("The number of dollar menu items cannot be more than you are spending! Please try again.");
                    goNext = false;
                }

                if (goNext && dollarMenuNumber > 6) {
                    badInputMessage.setVisibility(View.VISIBLE);
                    badInputMessage.setText("There are only 6 dollar menu items! Please try again.");
                    goNext = false;
                }

                if (goNext) {
                    badInputMessage.setVisibility(View.GONE);
                    input.setVisibility(View.GONE);
                    getDrinks();
                } else {
                    getDollarMenu();
                }
            }
        });
    }

    private void getDrinks() {
        final RadioGroup radioGroup = findViewById(R.id.drinksRadioGroup);
        radioGroup.setVisibility(View.VISIBLE);

        final ImageView imageView = findViewById(R.id.logoImage);
        final ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, imageView, "tacoBellLogo");

        whatToInput.setText("Do you want a fountain drink?");

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = radioGroup.getCheckedRadioButtonId();

                if (selectedId == findViewById(R.id.yesDrink).getId()) {
                    drinks = true;
                    goNext = true;
                } else if (selectedId == findViewById(R.id.noDrink).getId()) {
                    drinks = false;
                    goNext = true;
                } else {
                    goNext = false;
                }

                if (goNext) {
                    Intent intent = new Intent(view.getContext(), GeneratedOrder.class);
                    intent.putExtra("moneyAmount", moneyAmount);
                    intent.putExtra("dollarMenuNumber", dollarMenuNumber);
                    intent.putExtra("drinks", drinks);
                    startActivity(intent, options.toBundle());
                }
            }
        });
    }
}
