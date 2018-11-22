package team6.cmpt276.greenfoodchallenge.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import team6.cmpt276.greenfoodchallenge.R;


public class EditProfile extends AppCompatActivity {

    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private String userID = user.getUid();
    private EditText nameEdit;
    private Spinner dropdown;
    private ImageButton prevSelection=null;
    private ImageButton profile1;
    private ImageButton profile2;
    private ImageButton profile3;
    private ImageButton profile4;
    private ImageButton profile5;
    private ImageButton profile6;
    private DatabaseReference nameRef=database.child("user").child(userID).child("name");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        nameEdit= (EditText)findViewById(R.id.nameText);

        //making sure to set the default name to users current name
        nameRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String userName = dataSnapshot.getValue(String.class);
                if(userName!=null) {
                    nameEdit.setText(userName);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        dropdown = (Spinner) findViewById(R.id.citySpinner);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit Profile");

        Drawable threeLineIcon = ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_dehaze_black_24dp);
        toolbar.setOverflowIcon(threeLineIcon);


        final String[] cities = {   "Richmond", "Coquitlam", "Surrey", "Vancouver",
                "New Westminister", "Burnaby"};

        // set the drop down
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, cities);

        dropdown.setAdapter(adapter);


        //sets profile image based off image button selected

        profile1 = findViewById(R.id.profile1);
        profile1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setHighlight(profile1);
                database.child("user").child(userID).child("icon").setValue("bamboo");
            }
        });

        profile2 = findViewById(R.id.profile2);
        profile2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setHighlight(profile2);
                database.child("user").child(userID).child("icon").setValue("cherry");
            }
        });

        profile3 = findViewById(R.id.profile3);
        profile3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setHighlight(profile3);
                database.child("user").child(userID).child("icon").setValue("peanut");
            }
        });

        profile4 = findViewById(R.id.profile4);
        profile4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setHighlight(profile4);
                database.child("user").child(userID).child("icon").setValue("earth");
            }
        });

        profile5 = findViewById(R.id.profile5);
        profile5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setHighlight(profile5);
                database.child("user").child(userID).child("icon").setValue("tree");
            }
        });

        profile6 = findViewById(R.id.profile6);
        profile6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setHighlight(profile6);
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

                Intent intent = new Intent(v.getContext(), ProfileTab.class);

                startActivity(intent);
            }
        });
    }

    private void setHighlight(ImageButton selected){
        if(prevSelection!=null){
            prevSelection.setBackgroundResource(0);
        }
        selected.setBackgroundResource(R.drawable.highlight);
        prevSelection=selected;
    }



}
