package team6.cmpt276.greenfoodchallenge.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import team6.cmpt276.greenfoodchallenge.R;
import team6.cmpt276.greenfoodchallenge.classes.UserData;

public class ConsumptionQuiz1 extends AppCompatActivity {
    private UserData currentConsumption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumption_quiz1);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Calculate Consumption");

        final TextView proteinGramsCounter = findViewById(R.id.proteinGramsCounter);
        final TextView vegGramsCounter = findViewById(R.id.vegGramsCounter);

        final SeekBar proteinBar = findViewById(R.id.proteinSeekerBar);
        proteinBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int seekBarValue = 250;
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekBarValue = progress;
                proteinGramsCounter.setText(seekBarValue + "g");
            }
            public void onStartTrackingTouch(SeekBar seekBar) { }
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        final SeekBar vegBar =  findViewById(R.id.vegSeekerBar);
        vegBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int seekBarValue = 250;
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekBarValue = progress;
                vegGramsCounter.setText(seekBarValue + "g");
            }
            public void onStartTrackingTouch(SeekBar seekBar) { }
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });


        Button startButton = findViewById(R.id.nextButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uID = ""; //Attach userID
                currentConsumption = new UserData(uID,proteinBar.getProgress(),vegBar.getProgress(),true);
                Intent intent = new Intent(ConsumptionQuiz1.this, ConsumptionQuiz2.class);
                intent.putExtra("currentConsumption", currentConsumption);
                startActivity(intent);
            }
        });
    }

}
