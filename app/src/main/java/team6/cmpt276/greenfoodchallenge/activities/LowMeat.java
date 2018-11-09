package team6.cmpt276.greenfoodchallenge.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import team6.cmpt276.greenfoodchallenge.R;
import team6.cmpt276.greenfoodchallenge.classes.PlanPicker;
import team6.cmpt276.greenfoodchallenge.classes.UserData;

// This is the
public class LowMeat extends AppCompatActivity {

    private UserData suggestedConsumption;
    private PlanPicker planPicker;
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private String userID = user.getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_low_meat);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Plan Picker");

        Drawable threeLineIcon = ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_dehaze_black_24dp);
        toolbar.setOverflowIcon(threeLineIcon);

        database.child("current_consumptions").child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                suggestedConsumption = dataSnapshot.getValue(UserData.class);

                planPicker = new PlanPicker(suggestedConsumption);

                double consumptionValue = suggestedConsumption.getProteinPerMeal() * 50 / 100;
                TextView consumptionDescription = (TextView) findViewById(R.id.consumptionDescription);
                consumptionDescription.setText(consumptionValue + "g per meal");

                // Set up Seeker Bar
                final SeekBar consumption = (SeekBar)findViewById(R.id.consumptionSeekBar);
                consumption.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    int seekBarValue = 50;

                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        seekBarValue = progress;
                        TextView seekerDescrition = (TextView) findViewById(R.id.seekerDescription);
                        seekerDescrition.setText(seekBarValue + "% of current meal");

                        double consumptionValue = suggestedConsumption.getProteinPerMeal() * seekBarValue/100;
                        TextView consumptionDescription = (TextView) findViewById(R.id.consumptionDescription);
                        consumptionDescription.setText(consumptionValue + "g per meal");
                    }

                    public void onStartTrackingTouch(SeekBar seekBar) { }

                    public void onStopTrackingTouch (SeekBar seekBar) { }
                });

                Button button = findViewById(R.id.submit);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        double newProteinPerMeal = suggestedConsumption.getProteinPerMeal() * consumption.getProgress() / 100;
                        suggestedConsumption.setProteinPerMeal(newProteinPerMeal);
                        database.child("suggested_consumptions").child(userID).setValue(suggestedConsumption);
                        Intent intent = new Intent(LowMeat.this, ResultActivity2.class);
                        intent.putExtra("dietOption", "Low Meat Option");
                        startActivity(intent);
                    }
                });

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.user_dashboard:
                if (user.isAnonymous()){
                    startActivity(new Intent(this,UserLogin.class));
                    return true;
                } else {
                    startActivity(new Intent(this, UserDashboard.class));
                    return true;
                }
            case R.id.view_all_pledge:
                startActivity(new Intent(this,PledgeSummary.class));
                return true;
            case R.id.calculate_consumption:
                startActivity(new Intent(this,ConsumptionQuiz1.class));
                return true;
            case R.id.profile_login:
                if (user.isAnonymous()){
                    startActivity(new Intent(this,UserLogin.class));
                    return true;
                } else {
                    startActivity(new Intent (this, UserProfile.class));
                    return true;
                }
            case R.id.about_us:
                startActivity(new Intent(this,AboutActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
