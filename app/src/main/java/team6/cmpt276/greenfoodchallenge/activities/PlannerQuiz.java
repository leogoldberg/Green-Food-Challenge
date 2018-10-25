package team6.cmpt276.greenfoodchallenge.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import team6.cmpt276.greenfoodchallenge.R;
import team6.cmpt276.greenfoodchallenge.classes.ConsumptionClass;

public class PlannerQuiz extends AppCompatActivity {

    ConsumptionClass currentConsumption = new ConsumptionClass();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planner_quiz);
        currentConsumption.setProteinPerMeal(245);

        // Set up Meat Lover Option
        LinearLayout meatLover = (LinearLayout)findViewById(R.id.meatLover);
        meatLover.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {
                Intent  intent = new Intent(PlannerQuiz.this, MeatEater.class);
                intent.putExtra("serliazing-data",currentConsumption);
                startActivity(intent);
                }
        });

        // Set up Low Meat Option
        LinearLayout lowMeat = (LinearLayout) findViewById(R.id.lowMeat);
        lowMeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent = new Intent(PlannerQuiz.this, LowMeat.class);
                intent.putExtra("serializing-data",currentConsumption);
                startActivity(intent);
            }
        });


        // Set up Plant Based Option
        LinearLayout plantBased = (LinearLayout)findViewById(R.id.plantBased);
        plantBased.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



    }
}
