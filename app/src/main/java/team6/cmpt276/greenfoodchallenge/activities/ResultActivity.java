package team6.cmpt276.greenfoodchallenge.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import team6.cmpt276.greenfoodchallenge.R;
import team6.cmpt276.greenfoodchallenge.classes.UserData;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


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
        int total_amount_per_week = intent.getIntExtra("total_amount_per_week", -1);

        HashMap<String, Integer> times_per_week = (HashMap<String, Integer>)intent.getSerializableExtra("times_per_week");

        PieChart chart = findViewById(R.id.chart);

        List<PieEntry> entries = addEntries(times_per_week, total_amount_per_week);

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setValueFormatter(new PercentFormatter());

        chart.getLegend().setEnabled(false);
        chart.getDescription().setEnabled(false);

        PieData pieData = new PieData(dataSet);
        pieData.setValueTextSize(10f);
        chart.animateY(1500);
        chart.setData(pieData);
        chart.invalidate();

        UserData userData = new UserData(protein_per_meal,0);
        userData = setProteinFrequency(userData, times_per_week);

        double total = userData.getTotalco2();

        Log.i("ResultsActivity", "protein_per_meal: " + protein_per_meal);

        /*Consumption myConsumption = new Consumption();
        for(int i = 0; i < NUM_OF_ITEMS; i++){
            FoodData item = new FoodData(proteinItems[i], times_per_week[i], protein_per_meal);
            myConsumption.addItem(item);
        }
        //example
        double pork_co2e = myConsumption.getItemCO2e(PORK);
        double totalco2e = myConsumption.getTotalCO2e();
        Log.i("ResultsActivity", "pork_co2e: " + pork_co2e);
        Log.i("ResultsActivity", "totalco2e: " + totalco2e);
        */
    }

    public List<PieEntry> addEntries(HashMap<String, Integer> times_per_week,
                                     int total_amount_per_week) {
        List<PieEntry> entries = new ArrayList<>();

        Iterator it = times_per_week.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();

            String key = (String) pair.getKey();
            int value = (int) pair.getValue();
            float percentage = getPercentage(value, total_amount_per_week);

            entries.add(new PieEntry(percentage, key));
            it.remove();
        }

        return entries;
    }

    public float getPercentage(int count, int total) {
        return ((float) count / total) * 100;
    }

    public UserData setProteinFrequency(UserData userData, HashMap<String, Integer> times_per_week) {
        Iterator it = times_per_week.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();

            String key = (String) pair.getKey();
            int value = (int) pair.getValue();

            userData.setFoodFrequency(key, value);

            it.remove();
        }

        return userData;
    }
}
