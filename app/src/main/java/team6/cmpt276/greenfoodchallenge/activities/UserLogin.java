package team6.cmpt276.greenfoodchallenge.activities;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import team6.cmpt276.greenfoodchallenge.R;
import team6.cmpt276.greenfoodchallenge.classes.User;

public class UserLogin extends AppCompatActivity {
    private static final int RC_SIGN_IN = 9001;
    private FirebaseAuth mAuth;
    private static final String TAG = "UserLogin";
    private GoogleSignInClient mGoogleSignInClient;
    private CallbackManager mCallbackManager;
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        setContentView(R.layout.activity_user_login);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Login");

        Drawable threeLineIcon = ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_dehaze_black_24dp);
        toolbar.setOverflowIcon(threeLineIcon);

        FirebaseUser currentUser = mAuth.getCurrentUser();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
        mCallbackManager = CallbackManager.Factory.create();

        //loginButton.setReadPermissions(Arrays.asList(EMAIL));
        // If you are using in a fragment, call loginButton.setFragment(this);

        mCallbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = findViewById(R.id.login_button);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
                // ...
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
                // ...
            }
        });

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            user = mAuth.getCurrentUser();
                            saveUserToDatabase();
                            Intent intent = new Intent(UserLogin.this, HomeScreen.class);
                            startActivity(intent);
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(UserLogin.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);

                //Convert anonymous to permanent user
                String userToken = account.getIdToken();
                AuthCredential credential = GoogleAuthProvider.getCredential(userToken, null);

                mAuth.getCurrentUser().linkWithCredential(credential)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "linkWithCredential:success");
                                    user = task.getResult().getUser();
//                                    saveUserToDatabase(user);
                                    Intent intent = new Intent(UserLogin.this, ConsumptionQuiz1.class);
                                    startActivity(intent);
                                } else {
                                    Log.w(TAG, "linkWithCredential:failure", task.getException());
                                    /*Toast.makeText(UserLogin.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();*/
                                }
                            }
                        });
                firebaseAuthWithGoogle(account);

            } catch (ApiException e) {
                GoogleSignInAccount account = null;
                try {
                     account = task.getResult(ApiException.class);
                }
                catch(ApiException x){
                    Log.w(TAG, "failed to get user", e);
                }
                String userToken = account.getIdToken();
                AuthCredential credential = GoogleAuthProvider.getCredential(userToken, null);
                mAuth.signInWithCredential(credential)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                FirebaseUser currentUser = task.getResult().getUser();
                                // Merge prevUser and currentUser accounts and data
                                // ...
                            }
                        });
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                firebaseAuthWithGoogle(account);
                // ...
            }

        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            user = mAuth.getCurrentUser();
                            saveUserToDatabase();
                            Snackbar.make(findViewById(R.id.main_layout), "Authentication successful.", Snackbar.LENGTH_SHORT).show();
                            Intent intent = new Intent(UserLogin.this, HomeScreen.class);
                            startActivity(intent);
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Snackbar.make(findViewById(R.id.main_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            Intent intent = new Intent(UserLogin.this, HomeScreen.class);
                            startActivity(intent);
                            //updateUI(null);
                        }

                    }
                });

    }

    /*public boolean isUserLoggedIn(){
        FirebaseUser currentUser = mAuth.getCurrentUser();
        return (currentUser!=null);
    }*/

    private void saveUserToDatabase(){
        String userID = user.getUid();
        database.child("user").child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //User already exists on the database, do nothing
                if(dataSnapshot.exists()){
                    Log.d(TAG, "saveUserToDataBase: User already exists");
                }
                // User does not exist. Create a new user
                else {
                    String userID = user.getUid();
                    String email = user.getEmail();
                    String displayName = user.getDisplayName();

                    // If the above were null, iterate the provider data
                    // and set with the first non null data
                    for (UserInfo userInfo : user.getProviderData()) {
                        if (displayName == null && userInfo.getDisplayName() != null) {
                            displayName = userInfo.getDisplayName();
                        }
                        if (email == null && userInfo.getEmail() != null) {
                            email = userInfo.getEmail();
                        }
                    }
                    User currentUser = new User(displayName,email);
                    database.child("user").child(userID).setValue(currentUser);
                    Log.d(TAG, "saveUserToDataBase: Create a new User");
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
                    startActivity(new Intent(this, ProfileTab.class));
                    return true;
                }
            case R.id.view_all_pledge:
                startActivity(new Intent(this,PledgeSummary.class));
                return true;
            case R.id.calculate_consumption:
                startActivity(new Intent(this,ConsumptionQuiz1.class));
                return true;
            case R.id.about_us:
                startActivity(new Intent(this,AboutActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
