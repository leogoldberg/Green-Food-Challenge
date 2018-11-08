package team6.cmpt276.greenfoodchallenge.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import team6.cmpt276.greenfoodchallenge.R;
import team6.cmpt276.greenfoodchallenge.classes.PlanPicker;
import team6.cmpt276.greenfoodchallenge.classes.UserData;

public class MeatEater extends AppCompatActivity {

    private UserData suggestedConsumption;
    private PlanPicker planPicker;
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private String userID = user.getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meat_eater);

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

                ArrayList<Integer> displayOptionList = planPicker.getResource();

                //Set up onClickListener for the option 1
                LinearLayout option1 = findViewById(R.id.option1);

                final int option1Value = displayOptionList.get(0);
                ImageView image1 = findViewById(R.id.image1);
                image1.setImageResource(displayOptionList.get(1));
                TextView text1 = findViewById(R.id.text1);
                text1.setText(displayOptionList.get(2));

                option1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        planPicker.meatEater(suggestedConsumption,option1Value);
                        database.child("suggested_consumptions").child(userID).setValue(suggestedConsumption);
                        Intent intent = new Intent(MeatEater.this, ResultActivity2.class);
                        intent.putExtra("dietOption", "Meat Eater Option");
                        startActivity(intent);
                    }
                });

                // Set up onClickListener for the option 2
                final int option2Value = displayOptionList.get(3);
                LinearLayout option2 = findViewById(R.id.option2);
                ImageView image2 = findViewById(R.id.image2);
                image2.setImageResource(displayOptionList.get(4));
                TextView text2 = findViewById(R.id.text2);
                text2.setText(displayOptionList.get(5));

                option2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        planPicker.meatEater(suggestedConsumption,option2Value);
                        database.child("suggested_consumptions").child(userID).setValue(suggestedConsumption);
                        Intent intent = new Intent(MeatEater.this, ResultActivity2.class);
                        intent.putExtra("dietOption", "Meat Eater Option");
                        startActivity(intent);

                    }
                });

                //Set up onClickListener for the option 3
                LinearLayout option3 = findViewById(R.id.option3);
                if (displayOptionList.size() == 9) {
                    final int option3Value = displayOptionList.get(6);
                    ImageView image3 = findViewById(R.id.image3);
                    image3.setImageResource(displayOptionList.get(7));
                    TextView text3 = findViewById(R.id.text3);
                    text3.setText(displayOptionList.get(8));

                    option3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            planPicker.meatEater(suggestedConsumption,option3Value);
                            database.child("suggested_consumptions").child(userID).setValue(suggestedConsumption);
                            Intent intent = new Intent(MeatEater.this, ResultActivity2.class);
                            intent.putExtra("dietOption", "Meat Eater Option");
                            startActivity(intent);
                        }
                    });
                } else {
                    option3.setVisibility(View.INVISIBLE);
                }

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
                    //  startActivity(new Intent(this, DashBoardActivity.class));
                    return true;
                }
            case R.id.view_all_pledge:
                startActivity(new Intent(this,ViewAllPledges.class));
                return true;
            case R.id.calculate_consumption:
                startActivity(new Intent(this,ConsumptionQuiz1.class));
                return true;
            case R.id.profile_login:
                if (user.isAnonymous()){
                    startActivity(new Intent(this,UserLogin.class));
                    return true;
                } else {
                    // startActivity(new Intent this, UserDashboard.class);
                    return true;
                }
            case R.id.about_us:
                startActivity(new Intent(this,AboutActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
