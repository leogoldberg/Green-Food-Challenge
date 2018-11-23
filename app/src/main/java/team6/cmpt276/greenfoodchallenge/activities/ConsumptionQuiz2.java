package team6.cmpt276.greenfoodchallenge.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
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
import team6.cmpt276.greenfoodchallenge.classes.UserData;

public class ConsumptionQuiz2 extends AppCompatActivity {

    private UserData currentConsumption;
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private BottomNavigationView bottomNavigationView;
    private String userID = user.getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumption_quiz2);

        currentConsumption = new UserData();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Calculate Consumption");

        Drawable threeLineIcon = ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_dehaze_black_24dp);
        toolbar.setOverflowIcon(threeLineIcon);

        // Set up SeekBars
        for(int count = 0; count < 7; count++) {
            final SeekBar bar = findViewById(R.id.seekBar1 + count);
            final TextView value = findViewById(R.id.seekBarValue1 + count);
            final int finalCount = count;
            currentConsumption.setFoodFrequency(returnKey(finalCount),bar.getProgress());
            bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    value.setText(String.valueOf(progress)+" days");
                }
                public void onStartTrackingTouch(SeekBar seekBar) { }
                public void onStopTrackingTouch(SeekBar seekBar) {
                    currentConsumption.setFoodFrequency(returnKey(finalCount),bar.getProgress());
                }
            });
        }


        Button startButton = findViewById(R.id.submitQuiz);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.child("current_consumptions").child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        UserData consumptionDB = dataSnapshot.getValue(UserData.class);
                        currentConsumption.setProteinPerMeal(consumptionDB.getProteinPerMeal());
                        currentConsumption.setVegPerMeal(consumptionDB.getVegPerMeal());
                        database.child("current_consumptions").child(userID).setValue(currentConsumption);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
                Intent  intent = new Intent(ConsumptionQuiz2.this, ResultActivity.class);
                startActivity(intent);
            }
        });


        bottomNavigationView =findViewById(R.id.navbar);

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



    private String returnKey(int count) {
        String result = "";

        switch(count) {
            case 0 :
                result = "Beef";
                break;
            case 1 :
                result = "Pork";
                break;
            case 2 :
                result = "Chicken";
                break;
            case 3 :
                result = "Fish";
                break;
            case 4 :
                result = "Eggs";
                break;
            case 5 :
                result = "Beans";
                break;
            case 6 :
                result = "Vegetable";
                break;
        }

        return result;
    }
}
