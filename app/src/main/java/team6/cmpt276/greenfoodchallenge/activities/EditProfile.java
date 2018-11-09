package team6.cmpt276.greenfoodchallenge.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

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

}
