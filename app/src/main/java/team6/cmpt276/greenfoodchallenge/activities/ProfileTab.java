package team6.cmpt276.greenfoodchallenge.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import team6.cmpt276.greenfoodchallenge.classes.ViewPagerAdapter;

public class ProfileTab extends AppCompatActivity {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private FirebaseAuth mAuth;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private String userID = user.getUid();

    //set up profile data
    private DatabaseReference nameRef=database.child("user").child(userID).child("name");
    private DatabaseReference cityRef=database.child("user").child(userID).child("municipality");
    private DatabaseReference iconRef=database.child("user").child(userID).child("icon");
    private DatabaseReference emailRef=database.child("user").child(userID).child("email");
    private TextView nameView;
    private TextView cityView;
    private ImageView profileView;
    private TextView emailView;
    private Button edit;
    private BottomNavigationView bottomNavigationView;
    private Button addButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_tab);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Green Food Challenge");

        nameView = (TextView) findViewById(R.id.userName);
        cityView = (TextView) findViewById(R.id.municipality);
        emailView= (TextView) findViewById(R.id.email);
        profileView = (ImageView) findViewById(R.id.profilePic);
        edit = (Button) findViewById(R.id.editProfile);
        addButton = (Button) findViewById(R.id.add_button);

        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        //add tabs to userPledge and userMealFeed
        adapter.addFragment(new UserPledge(), "Pledge");
        adapter.addFragment(new UserMeal(), "Meal");
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);


        nameRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String userName = dataSnapshot.getValue(String.class);
                nameView.setText(userName);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        cityRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String city = dataSnapshot.getValue(String.class);
                cityView.setText(city);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        emailRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String email = dataSnapshot.getValue(String.class);
                emailView.setText(email);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        //setting profile picture
        iconRef.addValueEventListener(new ValueEventListener() {
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
                Intent intent = new Intent(ProfileTab.this, EditProfile.class);
                startActivity(intent);
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ProfileTab.this, AddMeal.class);
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
                    Intent intent = new Intent(ProfileTab.this, HomeScreen.class);
                    startActivity(intent);
                }
            });

        }

        bottomNavigationView =findViewById(R.id.navbar);
        bottomNavigationView.getMenu().findItem(R.id.profile).setChecked(true);

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
