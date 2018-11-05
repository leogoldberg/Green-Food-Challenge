package team6.cmpt276.greenfoodchallenge.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.auth.FirebaseUser;

import team6.cmpt276.greenfoodchallenge.R;
import team6.cmpt276.greenfoodchallenge.classes.UserData;

public class ConsumptionQuiz1 extends AppCompatActivity {
    private UserData currentConsumption;
    private DatabaseReference database;
    private TextView proteinGramsCounter;
    private TextView vegGramsCounter;
    private SeekBar vegBar;
    private Button nextButton;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumption_quiz1);

        database = FirebaseDatabase.getInstance().getReference();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Calculate Consumption");

        proteinGramsCounter = findViewById(R.id.proteinGramsCounter);
        vegGramsCounter = findViewById(R.id.vegGramsCounter);

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

        vegBar =  findViewById(R.id.vegSeekerBar);
        vegBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int seekBarValue = 250;
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekBarValue = progress;
                vegGramsCounter.setText(seekBarValue + "g");
            }
            public void onStartTrackingTouch(SeekBar seekBar) { }
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });


        nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uID = ""; //Attach userID
                currentConsumption = new UserData();
                currentConsumption.setProteinPerMeal(proteinBar.getProgress());
                currentConsumption.setVegPerMeal(vegBar.getProgress());
                setNextButton(currentConsumption);
                Intent intent = new Intent(ConsumptionQuiz1.this, ConsumptionQuiz2.class);
                startActivity(intent);
            }
        });
    }

    private void setNextButton (UserData currentConsumption) {
        final String userID = user.getUid();
        database.child("current_consumptions").child(userID).setValue(currentConsumption);
    }

}
