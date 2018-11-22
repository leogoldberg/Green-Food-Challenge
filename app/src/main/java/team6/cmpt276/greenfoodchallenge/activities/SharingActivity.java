package team6.cmpt276.greenfoodchallenge.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import team6.cmpt276.greenfoodchallenge.R;

public class SharingActivity extends AppCompatActivity {

    EditText userInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharing);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Share Pledge");

        Intent intent = getIntent();
        Bundle extras= intent.getExtras();
        String savedAmount = extras.getString("savedAmount");
        String dietPlan = extras.getString("dietPlanName");

        TextView mPledgeText = (TextView) findViewById(R.id.sharing_message);
        String plan;
        mPledgeText.setText("I pledge with "+ dietPlan + " I will save " + savedAmount + " per year!");

        Button submitButton = findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                userInput = findViewById(R.id.sharing_message);
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
