package team6.cmpt276.greenfoodchallenge.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import team6.cmpt276.greenfoodchallenge.R;


public class EditProfile extends AppCompatActivity {

    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private String userID = user.getUid();
    private EditText nameEdit;
    private Spinner dropdown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        nameEdit= (EditText)findViewById(R.id.nameText);
        dropdown = (Spinner) findViewById(R.id.citySpinner);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit Profile");

        Drawable threeLineIcon = ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_dehaze_black_24dp);
        toolbar.setOverflowIcon(threeLineIcon);


        final String[] cities = {   "Richmond", "Coquitlam", "Surrey", "Vancouver",
                "New Westminister", "Burnaby", "Undefined"};

        // set the drop down
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, cities);

        dropdown.setAdapter(adapter);


        //sets profile image based off image button selected

        ImageButton profile1 = findViewById(R.id.profile1);
        profile1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.child("user").child(userID).child("icon").setValue("bamboo");
            }
        });

        ImageButton profile2 = findViewById(R.id.profile2);
        profile2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.child("user").child(userID).child("icon").setValue("cherry");
            }
        });

        ImageButton profile3 = findViewById(R.id.profile3);
        profile3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.child("user").child(userID).child("icon").setValue("peanut");
            }
        });

        ImageButton profile4 = findViewById(R.id.profile4);
        profile4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.child("user").child(userID).child("icon").setValue("earth");
            }
        });

        ImageButton profile5 = findViewById(R.id.profile5);
        profile5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.child("user").child(userID).child("icon").setValue("tree");
            }
        });

        ImageButton profile6 = findViewById(R.id.profile6);
        profile6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.child("user").child(userID).child("icon").setValue("heart");
            }
        });

        //finish exits edit profile and sends back user data
        Button finish = findViewById(R.id.finish);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = dropdown.getSelectedItem().toString();

                database.child("user").child(userID).child("municipality").setValue(city);
                database.child("pledges").child(userID).child("municipality").setValue(city);

                String name = nameEdit.getText().toString();
                database.child("user").child(userID).child("name").setValue(name);

                Intent intent = new Intent(v.getContext(), UserProfile.class);

                startActivity(intent);
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
                    startActivity(new Intent(this, ProfileTab.class));
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
