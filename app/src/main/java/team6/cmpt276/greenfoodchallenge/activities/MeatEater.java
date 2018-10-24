package team6.cmpt276.greenfoodchallenge.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import team6.cmpt276.greenfoodchallenge.R;

public class MeatEater extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meat_eater);


        LinearLayout option1 = (LinearLayout)findViewById(R.id.option1);

        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // Set up Low Meat Option
        LinearLayout option2 = (LinearLayout) findViewById(R.id.option2);

        LinearLayout option3 = (LinearLayout)findViewById(R.id.option3);
    }
}
