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

    private boolean variety;

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

                TextView dollarSign = findViewById(R.id.dollarSign);
                dollarSign.setVisibility(View.GONE);
                if (goNext) {
                    badInputMessage.setVisibility(View.INVISIBLE);
                    getOrderMethod();
                } else {
                    getMoney();
                }
            }
        });
    }

    private void getOrderMethod() {
        input.setVisibility(View.GONE);

        final RadioGroup radioGroup = findViewById(R.id.drinksRadioGroup);
        radioGroup.setVisibility(View.VISIBLE);

        final RadioButton yesButton = findViewById(R.id.yesButton);
        final RadioButton noButton = findViewById(R.id.noButton);

        yesButton.setText("Variety");
        noButton.setText("Most Items");
        whatToInput.setText("Do want a random selection of items or the most items for the amount?");

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = radioGroup.getCheckedRadioButtonId();

                if (selectedId == findViewById(R.id.yesButton).getId()) {
                    variety = true;
                    goNext = true;
                } else if (selectedId == findViewById(R.id.noButton).getId()) {
                    variety = false;
                    goNext = true;
                } else {
                    goNext = false;
                }

                yesButton.setChecked(false);
                noButton.setChecked(false);

                if (goNext && variety) {
                    getDrinks();
                } else if (goNext) {
                    getDollarMenu();
                } else {
                    getOrderMethod();
                }
            }
        });
    }


    private void getDollarMenu() {
        RadioGroup radioGroup = findViewById(R.id.drinksRadioGroup);
        radioGroup.setVisibility(View.GONE);

        input.setVisibility(View.VISIBLE);
        whatToInput.setVisibility(View.VISIBLE);

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
                    getDrinks();
                } else {
                    getDollarMenu();
                }
            }
        });
    }

    private void getDrinks() {
        badInputMessage.setVisibility(View.GONE);
        input.setVisibility(View.GONE);

        final RadioGroup radioGroup = findViewById(R.id.drinksRadioGroup);
        radioGroup.setVisibility(View.VISIBLE);

        final ImageView imageView = findViewById(R.id.logoImage);
        final ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, imageView, "tacoBellLogo");

        final RadioButton yesButton = findViewById(R.id.yesButton);
        final RadioButton noButton = findViewById(R.id.noButton);

        yesButton.setText("Yes");
        noButton.setText("No");
        whatToInput.setText("Do you want a fountain drink?");

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = radioGroup.getCheckedRadioButtonId();

                if (selectedId == findViewById(R.id.yesButton).getId()) {
                    drinks = true;
                    goNext = true;
                } else if (selectedId == findViewById(R.id.noButton).getId()) {
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
                    intent.putExtra("variety", variety);
                    startActivity(intent, options.toBundle());
                }
            }
        });
    }
}
