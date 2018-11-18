package team6.cmpt276.greenfoodchallenge.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

import team6.cmpt276.greenfoodchallenge.R;

public class HomeScreen extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private static final String TAG = "HomeScreen";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Green Food Challenge");

        Button startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeScreen.this, ConsumptionQuiz1.class);
                startActivity(intent);
            }
        });

        //log out for testing
        //FirebaseAuth.getInstance().signOut();

        /* Authentication stuff */
        mAuth = FirebaseAuth.getInstance();
        /*
            Check if User is already logged in
            AccessToken accessToken = AccessToken.getCurrentAccessToken();
            boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
        */
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        boolean isLoggedIn = currentUser != null || (accessToken != null && !accessToken.isExpired());
        Button loginButton = findViewById(R.id.loginButton);
        if(isLoggedIn){
            loginButton.setText("Logout");
            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseAuth.getInstance().signOut();
                    LoginManager.getInstance().logOut();
                    Intent intent = new Intent(HomeScreen.this, HomeScreen.class);
                    startActivity(intent);
                }
            });
            for (UserInfo profile : currentUser.getProviderData()) {
                Log.d(TAG, profile.getDisplayName());
                Log.d(TAG, profile.getEmail());
            }
            //loginButton.setVisibility(View.GONE);
        }
        else {
            loginButton.setText("Login");
            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(HomeScreen.this, UserLogin.class);
                    startActivity(intent);
                }
            });

        }

        Log.d(TAG, "user is signed in");
    }


        @Override
        public void onStart() {
            super.onStart();
            // Check if user is signed in (non-null) and update UI accordingly.
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if(currentUser != null){ // User is signed in
                //TODO: Skip quiz and go to user profile
                Log.d(TAG, "user is signed in");
            }
            else {
                mAuth.signInAnonymously()
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signInAnonymously:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "signInAnonymously:failure", task.getException());
                                    Toast.makeText(HomeScreen.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        }

    public void seePledgeSummary(View v) {
        Intent myIntent = new Intent(HomeScreen.this, PledgeSummary.class);
        startActivity(myIntent);
    }

    public void seeMealFeed(View v) {
        Intent myIntent = new Intent(HomeScreen.this, MealFeed.class);
        startActivity(myIntent);
    }
}
