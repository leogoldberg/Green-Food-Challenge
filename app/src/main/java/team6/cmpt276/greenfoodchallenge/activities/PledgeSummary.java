package team6.cmpt276.greenfoodchallenge.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

import team6.cmpt276.greenfoodchallenge.R;

public class PledgeSummary extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pledge_summary);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Pledge Summary");

        BarChart chart = findViewById(R.id.chart);

        final List<String> list_x_axis_name = getLabels();
        List<BarEntry> entries = addEntries();

        BarDataSet set = new BarDataSet(entries, "BarDataSet");

        XAxis xAxis = chart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if (value >= 0) {
                    if (value <= list_x_axis_name.size() - 1) {
                        return list_x_axis_name.get((int) value);
                    }
                }
                return "";
            }
        });

        BarData data = new BarData(set);
        data.setBarWidth(0.9f); // set custom bar width
        chart.setData(data);
        chart.setFitBars(true); // make the x-axis fit exactly all bars
        chart.invalidate(); // refresh
    }

    private List<String> getLabels() {
        List<String> labels = new ArrayList<>();
        labels.add("label1");
        labels.add("label2");
        labels.add("label3");
        labels.add("label4");

        return labels;
    }

    private List<BarEntry> addEntries() {
        List<BarEntry> entries = new ArrayList<>();

        entries.add(new BarEntry(0f, 30f));
        entries.add(new BarEntry(1f, 80f));
        entries.add(new BarEntry(2f, 60f));
        entries.add(new BarEntry(3f, 50f));

        return entries;
    }

    public void seePledgeSummary(View v) {
        Intent myIntent = new Intent(PledgeSummary.this, ViewAllPledges.class);
        startActivity(myIntent);
    }

}
