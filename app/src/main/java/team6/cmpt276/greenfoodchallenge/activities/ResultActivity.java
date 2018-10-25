package team6.cmpt276.greenfoodchallenge.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import team6.cmpt276.greenfoodchallenge.R;
import team6.cmpt276.greenfoodchallenge.classes.PlanPicker;
import team6.cmpt276.greenfoodchallenge.classes.UserData;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // gets the intent variables
        Intent intent = getIntent();
        int protein_per_meal = intent.getIntExtra("protein_per_meal", -1);
        int total_amount_per_week = intent.getIntExtra("total_amount_per_week", -1);
        HashMap<String, Integer> times_per_week = (HashMap<String, Integer>)intent.getSerializableExtra("times_per_week");

        // sets up the pie charts
        PieChart chart = initializePieChart(R.id.chart);

        List<PieEntry> entries = addEntries(times_per_week, total_amount_per_week);
        PieDataSet dataSet = setPieDataSet(entries);

        PieData pieData = setPieData(dataSet);

        chart.setData(pieData);
        chart.invalidate();

        // set the frequency
        UserData userData = new UserData(protein_per_meal,0);
        userData = setProteinFrequency(userData, times_per_week);

        // set total text
        double total = userData.getTotalco2();
        TextView tv1 = findViewById(R.id.consumed_co2e);
        tv1.setText(Double.toString(total) + " CO2e");

        Button next = (Button) findViewById(R.id.reduceConsumption);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, PlannerQuiz.class);
                startActivity(intent);
            }
        });
    };

    private List<PieEntry> addEntries(HashMap<String, Integer> times_per_week,
                                     int total_amount_per_week) {
        List<PieEntry> entries = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : times_per_week.entrySet()) {
            String key = entry.getKey();
            int value = entry.getValue();
            float percentage = getPercentage(value, total_amount_per_week);

            entries.add(new PieEntry(percentage, key));
        }

        return entries;
    }

    private float getPercentage(int count, int total) {
        return ((float) count / total) * 100;
    }

    private UserData setProteinFrequency(UserData userData, HashMap<String, Integer> times_per_week) {
        for (Map.Entry<String, Integer> entry : times_per_week.entrySet()) {
            String key = entry.getKey();
            int value = entry.getValue();

            userData.setFoodFrequency(key, value);
        }

        return userData;
    }

    private PieDataSet setPieDataSet(List<PieEntry> entries) {
        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setValueFormatter(new PercentFormatter());

        return dataSet;
    }

    private PieChart initializePieChart(int id) {
        PieChart chart = findViewById(id);
        chart.getLegend().setEnabled(false);
        chart.getDescription().setEnabled(false);
        chart.animateY(1500);

        return chart;
    }

    private PieData setPieData(PieDataSet dataSet) {
        PieData pieData = new PieData(dataSet);
        pieData.setValueTextSize(10f);

        return pieData;
    }
}
