package team6.cmpt276.greenfoodchallenge.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

import team6.cmpt276.greenfoodchallenge.R;
import team6.cmpt276.greenfoodchallenge.classes.PlanPicker;
import team6.cmpt276.greenfoodchallenge.classes.UserData;

public class LowMeat extends AppCompatActivity {

    UserData currentConsumption;
    UserData suggestedConsumption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_low_meat);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Plan Picker");

        //Import current consumption from previous activity
        currentConsumption = (UserData) getIntent().getSerializableExtra("currentConsumption");
        suggestedConsumption = new UserData(currentConsumption);

        PlanPicker quiz = new PlanPicker(currentConsumption);

        double consumptionValue = suggestedConsumption.getProteinPerMeal()*50/100;
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

                double consumptionValue = suggestedConsumption.getProteinPerMeal() * seekBarValue/100;
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
                double newProteinPerMeal = suggestedConsumption.getProteinPerMeal() * consumption.getProgress() / 100;
                suggestedConsumption.setProteinPerMeal(newProteinPerMeal);
                TextView consumptionDescription = (TextView) findViewById(R.id.consumptionDescription);
                Intent intent = new Intent(LowMeat.this, ResultActivity2.class);
                intent.putExtra("currentConsumption",currentConsumption);
                intent.putExtra("suggestedConsumption", suggestedConsumption);
                startActivity(intent);
            }
        });

    }
}
