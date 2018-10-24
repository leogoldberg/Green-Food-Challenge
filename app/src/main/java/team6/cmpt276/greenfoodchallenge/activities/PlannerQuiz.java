package team6.cmpt276.greenfoodchallenge.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import team6.cmpt276.greenfoodchallenge.R;

public class PlannerQuiz extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planner_quiz);

        // Set up Meat Lover Option
        LinearLayout meatLover = (LinearLayout)findViewById(R.id.meatLover);
        meatLover.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {
                startActivity(new Intent(PlannerQuiz.this, MeatEater.class));
                }
        });

        // Set up Low Meat Option
        LinearLayout lowMeat = (LinearLayout) findViewById(R.id.lowMeat);
        lowMeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PlannerQuiz.this, LowMeat.class));
            }
        });


        // Set up Plant Based Option
        LinearLayout plantBased = (LinearLayout)findViewById(R.id.plantBased);
        plantBased.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PlannerQuiz.this, MeatEater.class));
            }
        });



    }
}
