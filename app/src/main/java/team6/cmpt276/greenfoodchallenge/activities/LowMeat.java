package team6.cmpt276.greenfoodchallenge.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

import team6.cmpt276.greenfoodchallenge.R;

public class LowMeat extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_low_meat);

        // Set up Seeker Bar
        SeekBar consumption = (SeekBar)findViewById(R.id.consumptionSeekBar);
        consumption.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            int seekBarValue = 50;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekBarValue = progress;
                TextView seekerDescrition = (TextView) findViewById(R.id.seekerDescription);
                seekerDescrition.setText(seekBarValue + "% of current meal");
            }

            public void onStartTrackingTouch(SeekBar seekBar){
            }

            public void onStopTrackingTouch (SeekBar seekBar) {
            }
        });

    }
}
