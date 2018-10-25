package team6.cmpt276.greenfoodchallenge.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import java.util.HashMap;

import team6.cmpt276.greenfoodchallenge.R;

public class ConsumptionQuiz2 extends AppCompatActivity {

    private static final int NUM_OF_ITEMS = 7;
    private int protein_per_meal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumption_quiz2);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Calculate Consumption");

        Intent intent = getIntent();
        protein_per_meal = intent.getIntExtra("protein_per_meal", -1);

        Button startButton = findViewById(R.id.nextButton2);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passIntents();
            }
        });

    }

    private void passIntents() {
        HashMap<String, Integer> times_per_week = new HashMap<String, Integer>();
        int total_amount_per_week = 0;

        for(int count = 0; count < NUM_OF_ITEMS; count++) {
            SeekBar bar = findViewById(R.id.seekBar1 + count);
            String key = returnKey(count);
            int input = bar.getProgress();

            times_per_week.put(key, input);
            total_amount_per_week += input;
        }

        Intent intent = new Intent(ConsumptionQuiz2.this, ResultActivity.class);
        intent.putExtra("times_per_week", times_per_week);
        intent.putExtra("protein_per_meal", protein_per_meal);
        intent.putExtra("total_amount_per_week", total_amount_per_week);

        startActivity(intent);
    }

    private String returnKey(int count) {
        String result = "";

        switch(count) {
            case 0 :
                result = "Pork";
                break;
            case 1 :
                result = "Beef";
                break;
            case 2 :
                result = "Chicken";
                break;
            case 3 :
                result = "Fish";
                break;
            case 4 :
                result = "Eggs";
                break;
            case 5 :
                result = "Beans";
                break;
            case 6 :
                result = "Vegetables";
                break;
        }

        return result;
    }
}
