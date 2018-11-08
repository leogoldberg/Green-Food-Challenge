package team6.cmpt276.greenfoodchallenge.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import team6.cmpt276.greenfoodchallenge.R;
import team6.cmpt276.greenfoodchallenge.classes.User;

public class UserProfile extends AppCompatActivity {

    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private FirebaseAuth mAuth;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private String userID = user.getUid();
    private DatabaseReference nameRef=database.child("users").child(userID).child("name");
    private DatabaseReference cityRef=database.child("users").child(userID).child("municipality");
    private DatabaseReference dietRef=database.child("pledge").child(userID).child("dietOption");
    private DatabaseReference iconRef=database.child("users").child(userID).child("icon");
    private TextView nameView= findViewById(R.id.userName);
    private TextView cityView= findViewById(R.id.municipality);
    private TextView dietView= findViewById(R.id.diet);
    private ImageView profileView=findViewById(R.id.profilePic);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profile");

        nameRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String userName = dataSnapshot.getValue(String.class);
                nameView.setText(userName);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        cityRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String city = dataSnapshot.getValue(String.class);
                cityView.setText(city);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        dietRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String diet = dataSnapshot.getValue(String.class);
                dietView.setText(diet);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        //setting profile picture
        iconRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String icon = dataSnapshot.getValue(String.class);
                setImage(profileView, icon);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        //logout button, goes to home screen
        mAuth = FirebaseAuth.getInstance();
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        boolean isLoggedIn = (currentUser != null&& !currentUser.isAnonymous()) || (accessToken != null && !accessToken.isExpired());
        Button loginButton = findViewById(R.id.loginButton);
        if(isLoggedIn){
            loginButton.setText("Logout");
            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseAuth.getInstance().signOut();
                    LoginManager.getInstance().logOut();
                    Intent intent = new Intent(UserProfile.this, HomeScreen.class);
                    startActivity(intent);
                }
            });

        }
    }

    private void setImage(ImageView profile, String textToShow) {
        if (textToShow.equals("cherry")) {
            profile.setImageResource(R.drawable.cherry);
        } else if (textToShow.equals("peanut")) {
            profile.setImageResource(R.drawable.peanut);
        } else if (textToShow.equals("bamboo")) {
            profile.setImageResource(R.drawable.bamboo);
        }
        else if (textToShow.equals("tree")) {
            profile.setImageResource(R.drawable.tree);
        }
        else if (textToShow.equals("earth")) {
            profile.setImageResource(R.drawable.earth);
        }
        else if (textToShow.equals("heart")) {
            profile.setImageResource(R.drawable.heart);
        }
    }
}
