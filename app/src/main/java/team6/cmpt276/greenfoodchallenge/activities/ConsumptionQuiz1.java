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

public class ConsumptionQuiz1 extends AppCompatActivity {
    private int protein_per_meal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumption_quiz1);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Calculate Consumption");
        final TextView gramsCounter = findViewById(R.id.gramsCounter);

        SeekBar proteinBar =  findViewById(R.id.seekBar);
        proteinBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                gramsCounter.setText(String.valueOf(progress)+"g");
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
        Button startButton = (Button) findViewById(R.id.nextButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextPage();
            }
        });
    }

    void nextPage()
    {
        SeekBar proteinBar =  findViewById(R.id.seekBar);
        protein_per_meal = proteinBar.getProgress();
        Intent intent = new Intent(ConsumptionQuiz1.this, ConsumptionQuiz2.class);
        intent.putExtra("protein_per_meal", protein_per_meal);
        startActivity(intent);
    }
}
