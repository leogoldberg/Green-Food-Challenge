package team6.cmpt276.greenfoodchallenge.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import team6.cmpt276.greenfoodchallenge.R;

public class ResultActivity extends AppCompatActivity {

    private static final int PORK = 0;
    private static final int BEEF = 1;
    private static final int CHICKEN = 2;
    private static final int FISH = 3;
    private static final int EGGS = 4;
    private static final int BEANS = 5;
    private static final int VEGETABLES = 6;
    private static final int NUM_OF_ITEMS = 7;

    /*private ProteinSource[] proteinItems = {
            new ProteinSource("Beef", 27),
            new ProteinSource("Pork", 12.1),
            new ProteinSource("Chicken", 6.9),
            new ProteinSource("Fish", 6.1),
            new ProteinSource("Eggs", 4.8),
            new ProteinSource("Beans", 2),
            new ProteinSource("Vegetables", 2)
    };*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Intent intent = getIntent();
        int protein_per_meal = intent.getIntExtra("protein_per_meal", -1);
        int[] times_per_week = intent.getIntArrayExtra("times_per_week");
        //Log.i("ResultsActivity", "protein_per_meal: " + protein_per_meal);
        //Log.i("ResultsActivity", "times_per_week[0]: " + times_per_week[0]);

        /*Consumption myConsumption = new Consumption();
        for(int i = 0; i < NUM_OF_ITEMS; i++){
            FoodData item = new FoodData(proteinItems[i], times_per_week[i], protein_per_meal);
            myConsumption.addItem(item);
        }
        //example
        double pork_co2e = myConsumption.getItemCO2e(PORK);
        double totalco2e = myConsumption.getTotalCO2e();
        Log.i("ResultsActivity", "pork_co2e: " + pork_co2e);
        Log.i("ResultsActivity", "totalco2e: " + totalco2e);*/
    }

}
