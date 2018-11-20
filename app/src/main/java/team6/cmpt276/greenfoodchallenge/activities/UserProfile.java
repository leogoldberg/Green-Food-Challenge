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
import android.widget.ImageView;
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

public class UserProfile extends AppCompatActivity {

    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private FirebaseAuth mAuth;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private String userID = user.getUid();
    private DatabaseReference nameRef=database.child("user").child(userID).child("name");
    private DatabaseReference cityRef=database.child("user").child(userID).child("municipality");
    private DatabaseReference dietRef=database.child("pledges").child(userID).child("dietOption");
    private DatabaseReference iconRef=database.child("user").child(userID).child("icon");
    private DatabaseReference emailRef=database.child("user").child(userID).child("email");
    private DatabaseReference pledgeRef=database.child("pledges").child(userID).child("saveAmount");
    private TextView nameView;
    private TextView cityView;
    private TextView dietView;
    private ImageView profileView;
    private TextView emailView;
    private TextView pledgeView;
    private Button edit;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        nameView = (TextView) findViewById(R.id.userName);
        cityView = (TextView) findViewById(R.id.municipality);
        dietView= (TextView) findViewById(R.id.diet);
        emailView= (TextView) findViewById(R.id.email);
        pledgeView = (TextView) findViewById(R.id.pledge);
        profileView = (ImageView) findViewById(R.id.profilePic);
        edit = (Button) findViewById(R.id.editProfile);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profile");

        Drawable threeLineIcon = ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_dehaze_black_24dp);
        toolbar.setOverflowIcon(threeLineIcon);


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

        emailRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String email = dataSnapshot.getValue(String.class);
                emailView.setText(email);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        pledgeRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String pledge = dataSnapshot.getValue(Double.class).toString();
                pledgeView.setText(pledge);
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

        //Edit Profile Button
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfile.this, EditProfile.class);
                startActivity(intent);
            }
        });


        //logout button, goes to home screen
        mAuth = FirebaseAuth.getInstance();
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        boolean isLoggedIn = (currentUser != null&& !currentUser.isAnonymous()) || (accessToken != null && !accessToken.isExpired());
        Button logoutButton = findViewById(R.id.logout);
        if(isLoggedIn){
            logoutButton.setText("Logout");
            logoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseAuth.getInstance().signOut();
                    LoginManager.getInstance().logOut();
                    Intent intent = new Intent(UserProfile.this, HomeScreen.class);
                    startActivity(intent);
                }
            });

        }

        bottomNavigationView =findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_feed:
                        startActivity(new Intent(bottomNavigationView.getContext(), MealFeed.class));
                        return true;
                    case R.id.view_all_pledge:
                        startActivity(new Intent(bottomNavigationView.getContext(),PledgeSummary.class));
                        return true;
                    case R.id.calculate_consumption:
                        startActivity(new Intent(bottomNavigationView.getContext(),ConsumptionQuiz1.class));
                        return true;
                    case R.id.about:
                        startActivity(new Intent(bottomNavigationView.getContext(),AboutActivity.class));
                    case R.id.profile:
                        if (user.isAnonymous()){
                            startActivity(new Intent(bottomNavigationView.getContext(),UserLogin.class));
                            return true;
                        } else {
                            startActivity(new Intent (bottomNavigationView.getContext(), UserProfile.class));
                            return true;
                        }
                    default:
                        return onNavigationItemSelected(item);
                }
            }
        });
    }

    private void setImage(ImageView profile, String textToShow) {
        if (textToShow == null) {

        }
        else if(textToShow.equals("cherry")) {
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
