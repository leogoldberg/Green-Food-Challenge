package team6.cmpt276.greenfoodchallenge.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import team6.cmpt276.greenfoodchallenge.R;
import team6.cmpt276.greenfoodchallenge.classes.PlanPicker;
import team6.cmpt276.greenfoodchallenge.classes.UserData;

//This activity will let user select their preferred meal plan
public class PlannerQuiz extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planner_quiz);
        final UserData currentConsumption = (UserData) getIntent().getSerializableExtra("currentConsumption");
        final PlanPicker planPicker = new PlanPicker(currentConsumption);

        //Set up the toolbar with custom Title
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Plan Picker");


        // Set up OnClickListener for Low Meat option and handle passing intents to Low Meat
        LinearLayout meatEater = findViewById(R.id.meatLover);
        if (planPicker.isVegetarian()){
            meatEater.setVisibility(View.INVISIBLE);
        } else {
            meatEater.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent  intent = new Intent(PlannerQuiz.this, MeatEater.class);
                    intent.putExtra("currentConsumption",currentConsumption);
                    startActivity(intent);
                }
            });
        }

        // Set up OnClickListener for Low Meat option and handle passing intents to Low Meat
        LinearLayout lowMeat = findViewById(R.id.lowMeat);
        lowMeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent = new Intent(PlannerQuiz.this, LowMeat.class);
                intent.putExtra("currentConsumption",currentConsumption);
                startActivity(intent);
            }
        });


        // Set up OnClickListener for plant based option, calculate the suggested consumption and handle passing intents to Result Activity
        LinearLayout plantBased = findViewById(R.id.plantBased);
        plantBased.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserData suggestedConsumption = new UserData(currentConsumption, false);
                suggestedConsumption = planPicker.plantBased(suggestedConsumption);

                Intent intent = new Intent(PlannerQuiz.this, ResultActivity2.class);
                intent.putExtra("currentConsumption",currentConsumption);
                intent.putExtra("suggestedConsumption", suggestedConsumption);
                startActivity(intent);
            }
        });

    }
}
