package team6.cmpt276.greenfoodchallenge.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import team6.cmpt276.greenfoodchallenge.R;
import team6.cmpt276.greenfoodchallenge.classes.PlanPicker;

public class MeatEater extends AppCompatActivity {

    PlanPicker planPicker = new PlanPicker();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meat_eater);

        setupDisplay();

        //Set up onClickListener for the option 1
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

    // Set up the Option List according to user info
    private void setupDisplay() {
        ArrayList<Integer> displayOptionList = planPicker.getResource();

        ImageView image1 = (ImageView) findViewById(R.id.image1);
        image1.setImageResource(displayOptionList.get(0));

        TextView text1 = (TextView) findViewById(R.id.text1);
        text1.setText(displayOptionList.get(1));

        ImageView image2 = (ImageView) findViewById(R.id.image2);
        image2.setImageResource(displayOptionList.get(2));

        TextView text2 = (TextView) findViewById(R.id.text2);
        text2.setText(displayOptionList.get(3));

        ImageView image3 = (ImageView) findViewById(R.id.image3);
        image3.setImageResource(displayOptionList.get(4));

        TextView text3 = (TextView) findViewById(R.id.text3);
        text3.setText(displayOptionList.get(5));
    }
}
