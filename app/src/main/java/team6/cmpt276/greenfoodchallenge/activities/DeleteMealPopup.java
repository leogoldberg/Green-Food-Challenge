package team6.cmpt276.greenfoodchallenge.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import team6.cmpt276.greenfoodchallenge.R;

public class DeleteMealPopup extends AppCompatActivity {

    private DatabaseReference database = FirebaseDatabase.getInstance().getReference("meals");
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private String userID = user.getUid();

  //  Intent intent = getIntent();
   // Bundle extras= intent.getExtras();
   // int position = extras.getInt("position");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_meal_popup);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((int) (width * 0.80), (int) (height * 0.40));

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item_snapshot : dataSnapshot.getChildren()) {
                    final String key = item_snapshot.getKey();
                    //correct key should be identified here. tried manually typing in the key, but item did not get removed from database
                    Button noButton = findViewById(R.id.no_button);
                    noButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            finish();
                        }
                    });

                    Button yesButton = findViewById(R.id.yes_button);
                    yesButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (key == "LRimlsuN9raCsoJN9RR"){
                                database.child(key).removeValue();
                            }

                            finish();

                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

