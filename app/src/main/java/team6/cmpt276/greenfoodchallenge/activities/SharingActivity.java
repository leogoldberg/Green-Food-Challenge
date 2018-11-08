package team6.cmpt276.greenfoodchallenge.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

import team6.cmpt276.greenfoodchallenge.R;
import team6.cmpt276.greenfoodchallenge.classes.UserData;

public class SharingActivity extends AppCompatActivity {
    private UserData suggestedConsumption;
    private UserData currentConsumption;
    private double saveAmount;
    private String dietOption;

    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();
   // private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
   // private String userID = user.getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharing);

        final DatabaseReference firebasePledges = database.child("pledges");
        firebasePledges.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot item_snapshot : dataSnapshot.getChildren()) {
                    Map<String, Object> curPledge = (Map<String, Object>) item_snapshot.getValue();

                    dietOption = String.valueOf(curPledge.get("dietOption"));
                    saveAmount = Double.valueOf(curPledge.get("saveAmount").toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        TextView mPledgeText = (TextView) findViewById(R.id.sharing_message);
        String plan;
        mPledgeText.setText("I pledge with "+ dietOption + "I will save " + saveAmount + "CO2e.");

        Button submitButton = findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                EditText userInput = (EditText) findViewById(R.id.sharing_message);
                String shareMessage = userInput.getText().toString();
                intent.putExtra(Intent.EXTRA_TEXT, shareMessage);

                intent.setType("text/plain");
                startActivity(intent.createChooser(intent, getResources().getText(R.string.share_guide)));
            }
        });

        Button closeButton = findViewById(R.id.close_button);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
