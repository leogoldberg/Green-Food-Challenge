package team6.cmpt276.greenfoodchallenge.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

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
}
