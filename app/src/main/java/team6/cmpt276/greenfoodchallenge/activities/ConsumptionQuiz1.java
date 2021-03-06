package team6.cmpt276.greenfoodchallenge.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import team6.cmpt276.greenfoodchallenge.R;
import team6.cmpt276.greenfoodchallenge.classes.UserData;

public class ConsumptionQuiz1 extends AppCompatActivity {
    private UserData currentConsumption;
    private DatabaseReference database;
    private TextView proteinGramsCounter;
    private TextView vegGramsCounter;
    private SeekBar vegBar;
    private Button nextButton;
    private BottomNavigationView bottomNavigationView;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumption_quiz1);

        database = FirebaseDatabase.getInstance().getReference();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Calculate Consumption");

        Drawable threeLineIcon = ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_dehaze_black_24dp);
        toolbar.setOverflowIcon(threeLineIcon);

        proteinGramsCounter = findViewById(R.id.proteinGramsCounter);
        vegGramsCounter = findViewById(R.id.vegGramsCounter);

        final SeekBar proteinBar = findViewById(R.id.proteinSeekerBar);
        proteinBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int seekBarValue = 110;
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekBarValue = progress;
                proteinGramsCounter.setText(seekBarValue + "g");
            }
            public void onStartTrackingTouch(SeekBar seekBar) { }
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        vegBar =  findViewById(R.id.vegSeekerBar);
        vegBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int seekBarValue = 110;
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

        bottomNavigationView =findViewById(R.id.navbar);
        bottomNavigationView.getMenu().findItem(R.id.calculate_consumption).setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    //add case feed after feed activity pushed
                    case R.id.action_feed:
                        if (user.isAnonymous()){
                            startActivity(new Intent(bottomNavigationView.getContext(),UserLogin.class));
                            return true;
                        } else {
                            startActivity(new Intent(bottomNavigationView.getContext(), MealFeed.class));;
                            return true;
                        }
                    case R.id.view_all_pledge:
                        if (user.isAnonymous()){
                            startActivity(new Intent(bottomNavigationView.getContext(),UserLogin.class));
                            return true;
                        } else {
                            startActivity(new Intent(bottomNavigationView.getContext(),PledgeSummary.class));
                            return true;
                        }
                    case R.id.calculate_consumption:
                        startActivity(new Intent(bottomNavigationView.getContext(),ConsumptionQuiz1.class));
                        return true;
                    case R.id.about:
                        startActivity(new Intent(bottomNavigationView.getContext(),AboutActivity.class));
                        return true;
                    case R.id.profile:
                        if (user.isAnonymous()){
                            startActivity(new Intent(bottomNavigationView.getContext(),UserLogin.class));
                            return true;
                        } else {
                            startActivity(new Intent (bottomNavigationView.getContext(), ProfileTab.class));
                            return true;
                        }
                    default:
                        return false;
                }
            }
        });


    }

    private void setNextButton (UserData currentConsumption) {
        final String userID = user.getUid();
        database.child("current_consumptions").child(userID).setValue(currentConsumption);
    }
}
