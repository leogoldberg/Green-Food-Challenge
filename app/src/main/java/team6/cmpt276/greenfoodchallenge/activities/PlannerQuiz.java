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
import android.widget.LinearLayout;

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

//This activity will let user select their preferred meal plan
public class PlannerQuiz extends AppCompatActivity {

    private UserData currentConsumption;
    private UserData suggestedConsumption;
    private PlanPicker planPicker;
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private String userID = user.getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planner_quiz);

        //Set up the toolbar with custom Title
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Plan Picker");

        Drawable threeLineIcon = ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_dehaze_black_24dp);
        toolbar.setOverflowIcon(threeLineIcon);

        database.child("current_consumptions").child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentConsumption = dataSnapshot.getValue(UserData.class);
                suggestedConsumption = new UserData(currentConsumption);

                planPicker = new PlanPicker(currentConsumption);

                // Set up OnClickListener for Low Meat option and handle passing intents to Low Meat
                LinearLayout meatEater = findViewById(R.id.meatLover);
                if (planPicker.isVegetarian()){
                    meatEater.setVisibility(View.INVISIBLE);
                } else {
                    meatEater.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent  intent = new Intent(PlannerQuiz.this, MeatEater.class);
                            startActivity(intent);
                        }
                    });
                }

                // Set up OnClickListener for Low Meat option and handle passing intents to Low Meat
                LinearLayout lowMeat = findViewById(R.id.lowMeat);
                lowMeat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent  intent = new Intent(PlannerQuiz.this, LowMeat.class);
                        startActivity(intent);
                    }
                });


                // Set up OnClickListener for plant based option, calculate the suggested consumption and handle passing intents to Result Activity
                LinearLayout plantBased = findViewById(R.id.plantBased);
                plantBased.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        suggestedConsumption = planPicker.plantBased(suggestedConsumption);
                        Intent intent = new Intent(PlannerQuiz.this, ResultActivity2.class);
                        intent.putExtra("dietOption","Plant Based Diet");
                        database.child("suggested_consumptions").child(userID).setValue(suggestedConsumption);
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
