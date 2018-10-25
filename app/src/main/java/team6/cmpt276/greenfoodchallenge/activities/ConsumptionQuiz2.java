package team6.cmpt276.greenfoodchallenge.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

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

    void passIntents()
    {
        int[] times_per_week = new int[NUM_OF_ITEMS];
        for(int i = 0; i < NUM_OF_ITEMS; i++){
            SeekBar bar = findViewById(R.id.seekBar1 + i);
            times_per_week[i] = bar.getProgress();
        }
        Intent intent = new Intent(ConsumptionQuiz2.this, ResultActivity.class);
        intent.putExtra("times_per_week", times_per_week);
        intent.putExtra("protein_per_meal", protein_per_meal);
        startActivity(intent);
    }
}
