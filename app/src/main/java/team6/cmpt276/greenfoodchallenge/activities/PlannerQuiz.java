package team6.cmpt276.greenfoodchallenge.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import team6.cmpt276.greenfoodchallenge.R;
import team6.cmpt276.greenfoodchallenge.classes.PlanPicker;
import team6.cmpt276.greenfoodchallenge.classes.UserData;

public class PlannerQuiz extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final UserData currentConsumption = new UserData(250,250);
        currentConsumption.setFoodFrequency("Beef",0);
        currentConsumption.setFoodFrequency("Pork",0);
        currentConsumption.setFoodFrequency("Chicken",5);
        currentConsumption.setFoodFrequency("Fish",3);
        currentConsumption.setFoodFrequency("Eggs",0);
        currentConsumption.setFoodFrequency("Beans",5);

        final PlanPicker planPicker = new PlanPicker(currentConsumption);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planner_quiz);

        LinearLayout meatEater = (LinearLayout) findViewById(R.id.meatLover);
        if (planPicker.isVegetarian()){
            meatEater.setVisibility(View.INVISIBLE);
        }
        else {
            meatEater.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent  intent = new Intent(PlannerQuiz.this, MeatEater.class);
                    intent.putExtra("currentConsumption",currentConsumption);
                    startActivity(intent);
                }
            });
        }

        // Set up Low Meat Option
        LinearLayout lowMeat = (LinearLayout) findViewById(R.id.lowMeat);
        lowMeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent = new Intent(PlannerQuiz.this, LowMeat.class);
                intent.putExtra("currentConsumption",currentConsumption);
                startActivity(intent);
            }
        });


        // Set up Plant Based Option
        LinearLayout plantBased = (LinearLayout)findViewById(R.id.plantBased);
        plantBased.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserData suggestedConsumption = new UserData(currentConsumption);
                planPicker.plantBased(suggestedConsumption);
                Intent intent = new Intent(PlannerQuiz.this, ResultActivity2.class);
                intent.putExtra("currentConsumption",currentConsumption);
                intent.putExtra("suggestedConsumption", suggestedConsumption);
                startActivity(intent);

            }
        });



    }
}
