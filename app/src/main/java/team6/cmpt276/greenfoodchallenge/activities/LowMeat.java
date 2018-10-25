package team6.cmpt276.greenfoodchallenge.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

import team6.cmpt276.greenfoodchallenge.R;
import team6.cmpt276.greenfoodchallenge.classes.ConsumptionClass;
import team6.cmpt276.greenfoodchallenge.classes.PlanPicker;

public class LowMeat extends AppCompatActivity {
    PlanPicker quiz = new PlanPicker();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_low_meat);

        //Import current consumption from previous activity
        final ConsumptionClass currentConsumption = (ConsumptionClass)getIntent().getSerializableExtra("serializing-data");
        final ConsumptionClass suggestedConsumption = currentConsumption;

        float consumptionValue = suggestedConsumption.getProteinPerMeal()*50/100;
        TextView consumptionDescription = (TextView) findViewById(R.id.consumptionDescription);
        consumptionDescription.setText(consumptionValue + "g per meal");


        // Set up Seeker Bar
        final SeekBar consumption = (SeekBar)findViewById(R.id.consumptionSeekBar);
        consumption.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            int seekBarValue = 50;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekBarValue = progress;
                TextView seekerDescrition = (TextView) findViewById(R.id.seekerDescription);
                seekerDescrition.setText(seekBarValue + "% of current meal");

                float consumptionValue = suggestedConsumption.getProteinPerMeal()*seekBarValue/100;
                TextView consumptionDescription = (TextView) findViewById(R.id.consumptionDescription);
                consumptionDescription.setText(consumptionValue + "g per meal");

            }

            public void onStartTrackingTouch(SeekBar seekBar){
            }

            public void onStopTrackingTouch (SeekBar seekBar) {

            }
        });

        Button button = (Button) findViewById(R.id.submit);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quiz.lowMeat(suggestedConsumption,consumption.getProgress());
                TextView consumptionDescription = (TextView) findViewById(R.id.consumptionDescription);
                consumptionDescription.setText(suggestedConsumption.getProteinPerMeal() + "g per meal is the final answer");
                ArrayList<ConsumptionClass> consumption = new ArrayList<>();
                consumption.add(currentConsumption);
                consumption.add(suggestedConsumption);
                Intent intent = new Intent(LowMeat.this, PlannerQuiz.class);
                intent.putExtra("serializing-data",consumption);
                startActivity(intent);
            }
        });

    }
}
