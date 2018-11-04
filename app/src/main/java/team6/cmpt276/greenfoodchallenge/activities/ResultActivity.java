package team6.cmpt276.greenfoodchallenge.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import team6.cmpt276.greenfoodchallenge.R;
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

    private UserData currentConsumption;

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

    /**
     * Add entries based on the user input
     * @param currentConsumption        an object that holds all the user inputs
     * @param total_amount_per_week     the total frequency they eat protein products
     * @return the list of entries to show on the pie chart
     */
    private List<PieEntry> addEntries(UserData currentConsumption,
                                     int total_amount_per_week) {
        List<PieEntry> entries = new ArrayList<>();

        ArrayList<String> foodNames = currentConsumption.getFoodNames();
        ArrayList<Integer> userFoodData = currentConsumption.getUserFoodData();

        for(int i = 0; i < foodNames.size(); i++) {
            String curFoodName = foodNames.get(i);
            int curUserData = userFoodData.get(i);

            float percentage = getPercentage(curUserData, total_amount_per_week);

            if(percentage > 0) {
                entries.add(new PieEntry(percentage, curFoodName));
            }
        }


        return entries;
    }

    /**
     * Gets the percentage based on the user input
     * @param count     the frequeuncy of the user eats protein products
     * @param total     the total amount of protein products
     * @return          the percentage based on the count
     */
    private float getPercentage(int count, int total) {
        return ((float) count / total) * 100;
    }

    /**
     * Set the piedata and set the colors and how they display the content
     * @param entries   the list of entries
     * @return          returns the dataset
     */
    private PieDataSet setPieDataSet(List<PieEntry> entries) {
        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setValueFormatter(new PercentFormatter());

        return dataSet;
    }

    /**
     * Creates the Piechart
     * @param id    the id of the element
     * @return      returns the piechart
     */
    private PieChart initializePieChart(int id) {
        PieChart chart = findViewById(id);
        chart.getLegend().setEnabled(false);
        chart.getDescription().setEnabled(false);
        chart.animateY(1500);

        return chart;
    }

    /**
     * Set the the pie data
     * @param dataSet       the data in the pie chart
     * @return              the pie data 
     */
    private PieData setPieData(PieDataSet dataSet) {
        PieData pieData = new PieData(dataSet);
        pieData.setValueTextSize(10f);

        return pieData;
    }
}
