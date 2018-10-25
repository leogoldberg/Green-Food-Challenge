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
import team6.cmpt276.greenfoodchallenge.classes.FoodData;
import team6.cmpt276.greenfoodchallenge.classes.PlanPicker;
import team6.cmpt276.greenfoodchallenge.classes.UserData;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;


public class ResultActivity extends AppCompatActivity {

    UserData currentConsumption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Consumption Result");

        currentConsumption = (UserData) getIntent().getSerializableExtra("currentConsumption");
        int total_amount_per_week = currentConsumption.getTotalFrequency();

        // sets up the pie charts
        PieChart chart = initializePieChart(R.id.chart);
        List<PieEntry> entries = addEntries(currentConsumption, total_amount_per_week);
        PieDataSet dataSet = setPieDataSet(entries);

        PieData pieData = setPieData(dataSet);

        chart.setData(pieData);
        chart.invalidate();

        // set total text
        double total = currentConsumption.getTotalco2perYear();
        TextView tv1 = findViewById(R.id.consumed_co2e);
        tv1.setText(Math.round(total) + " CO2e ");

        Button next = findViewById(R.id.reduceConsumption);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, PlannerQuiz.class);
                intent.putExtra("currentConsumption", currentConsumption);
                startActivity(intent);
            }
        });
    };

    private List<PieEntry> addEntries(UserData currentConsumption,
                                     int total_amount_per_week) {
        List<PieEntry> entries = new ArrayList<>();

        List<String> foodNames = currentConsumption.getFoodNames();
        List<FoodData> userFoodData = currentConsumption.getUserFoodData();

        for(int i = 0; i < foodNames.size(); i++) {
            String curFoodName = foodNames.get(i);
            FoodData curUserData = userFoodData.get(i);

            float percentage = getPercentage(curUserData.getFrequency(), total_amount_per_week);

            if(percentage > 0) {
                entries.add(new PieEntry(percentage, curFoodName));
            }
        }


        return entries;
    }

    private float getPercentage(int count, int total) {
        return ((float) count / total) * 100;
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
