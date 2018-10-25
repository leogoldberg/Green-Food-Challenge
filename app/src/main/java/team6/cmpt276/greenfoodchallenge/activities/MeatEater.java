package team6.cmpt276.greenfoodchallenge.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import team6.cmpt276.greenfoodchallenge.R;
import team6.cmpt276.greenfoodchallenge.classes.PlanPicker;
import team6.cmpt276.greenfoodchallenge.classes.UserData;

public class MeatEater extends AppCompatActivity {

    private UserData currentConsumption;
    private UserData suggestedConsumption;
    private PlanPicker planPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meat_eater);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Plan Picker");

        currentConsumption = (UserData) getIntent().getSerializableExtra("currentConsumption");
        suggestedConsumption = new UserData(currentConsumption);

        planPicker = new PlanPicker(currentConsumption);

        ArrayList<Integer> displayOptionList = planPicker.getResource();

        //Set up onClickListener for the option 1
        LinearLayout option1 = findViewById(R.id.option1);

        final int option1Value = displayOptionList.get(0);
        ImageView image1 = findViewById(R.id.image1);
        image1.setImageResource(displayOptionList.get(1));
        TextView text1 = findViewById(R.id.text1);
        text1.setText(displayOptionList.get(2));

        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                planPicker.meatEater(suggestedConsumption,option1Value);
                Intent intent = new Intent(MeatEater.this, ResultActivity2.class);
                intent.putExtra("currentConsumption",currentConsumption);
                intent.putExtra("suggestedConsumption", suggestedConsumption);
                startActivity(intent);
            }
        });

        // Set up onClickListener for the option 2
        final int option2Value = displayOptionList.get(3);
        LinearLayout option2 = findViewById(R.id.option2);
        ImageView image2 = findViewById(R.id.image2);
        image2.setImageResource(displayOptionList.get(4));
        TextView text2 = findViewById(R.id.text2);
        text2.setText(displayOptionList.get(5));

        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                planPicker.meatEater(suggestedConsumption,option2Value);
                Intent intent = new Intent(MeatEater.this, ResultActivity2.class);
                intent.putExtra("currentConsumption",currentConsumption);
                intent.putExtra("suggestedConsumption", suggestedConsumption);
                startActivity(intent);

            }
        });

        //Set up onClickListener for the option 3
        LinearLayout option3 = findViewById(R.id.option3);
        if (displayOptionList.size() == 9) {
            final int option3Value = displayOptionList.get(6);
            ImageView image3 = findViewById(R.id.image3);
            image3.setImageResource(displayOptionList.get(7));
            TextView text3 = findViewById(R.id.text3);
            text3.setText(displayOptionList.get(8));

            option3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    planPicker.meatEater(suggestedConsumption,option3Value);
                    Intent intent = new Intent(MeatEater.this, ResultActivity2.class);
                    intent.putExtra("currentConsumption",currentConsumption);
                    intent.putExtra("suggestedConsumption", suggestedConsumption);
                    startActivity(intent);
                }
            });
        } else {
            option3.setVisibility(View.INVISIBLE);
        }

    }

}
