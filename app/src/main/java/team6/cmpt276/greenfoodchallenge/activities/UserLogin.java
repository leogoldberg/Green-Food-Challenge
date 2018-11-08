package team6.cmpt276.greenfoodchallenge.activities;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import team6.cmpt276.greenfoodchallenge.R;
import team6.cmpt276.greenfoodchallenge.classes.User;

public class UserLogin extends AppCompatActivity {
    private static final int RC_SIGN_IN = 9001;
    private FirebaseAuth mAuth;
    private static final String TAG = "UserLogin";
    private GoogleSignInClient mGoogleSignInClient;
    private CallbackManager mCallbackManager;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_user_login);
        FirebaseUser currentUser = mAuth.getCurrentUser();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mDatabase = FirebaseDatabase.getInstance().getReference();
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
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
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
        Log.d(TAG, "IT RANn");
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();

                            Log.d(TAG, "IT Ran");
                            saveToDatabase(user);
                            Toast.makeText(UserLogin.this, "Authentication successful.",
                                    Toast.LENGTH_SHORT).show();

                            // Intent intent = new Intent(UserLogin.this, HomeScreen.class);
                            //startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(UserLogin.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

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
                                    FirebaseUser user = task.getResult().getUser();
                                    Toast.makeText(UserLogin.this, "Authentication successful.",
                                            Toast.LENGTH_SHORT).show();
                                    saveToDatabase(user);
                                    //Intent intent = new Intent(UserLogin.this, HomeScreen.class);
                                    //startActivity(intent);
                                } else {
                                    Log.w(TAG, "linkWithCredential:failure", task.getException());
                                    /*Toast.makeText(UserLogin.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();*/
                                }
                            }
                        });
                firebaseAuthWithGoogle(account);

            } catch (ApiException e) { /* Account already linked  */
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
                            Log.d(TAG, "aInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            String key = mDatabase.child("users").push().getKey();
                            User aUser = new User(user.getDisplayName(), user.getEmail());
                            saveToDatabase(user);
                            Log.d(TAG, "IT RANn");
                            Toast.makeText(UserLogin.this, "Authentication successful.", Toast.LENGTH_SHORT).show();
                            Snackbar.make(findViewById(R.id.main_layout), "Authentication successful.", Snackbar.LENGTH_SHORT).show();
                            //Intent intent = new Intent(UserLogin.this, HomeScreen.class);
                            //startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Snackbar.make(findViewById(R.id.main_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                        }

                    }
                });

    }


    private void saveToDatabase(FirebaseUser user){
        String userID = user.getUid();
        Log.d(TAG, "saaaignInWithCredential:success");
        //String key = mDatabase.child("users").push().getKey();
        mDatabase.child("users").child(userID).child("name").setValue(user.getDisplayName());
        mDatabase.child("users").child(userID).child("email").setValue(user.getEmail());
    }
    /*public boolean isUserLoggedIn(){
        FirebaseUser currentUser = mAuth.getCurrentUser();
        return (currentUser!=null);
    }*/

}
