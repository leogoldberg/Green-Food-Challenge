package team6.cmpt276.greenfoodchallenge.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import team6.cmpt276.greenfoodchallenge.R;
import team6.cmpt276.greenfoodchallenge.classes.Pledge;

public class PledgeSummary extends AppCompatActivity {
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private String userID = user.getUid();
    private Map<String, ArrayList> pledges;
    private HashMap<String, String> userNames;
    private HashMap<String, String> pictures;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pledge_summary);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Pledge Summary");

        final String[] cities = {   "Metro Vancouver","Richmond", "Coquitlam", "Surrey",
                                    "Vancouver", "New Westminister", "Burnaby", "Undefined"};
        Drawable threeLineIcon = ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_dehaze_black_24dp);
        toolbar.setOverflowIcon(threeLineIcon);

        final String[] cities = {   "Richmond", "Coquitlam", "Surrey", "Vancouver",
                                    "New Westminister", "Burnaby"};

        // set the hashmap
        pledges = createMap(cities);
        userNames = new HashMap<>();
        pictures = new HashMap<>();

        // set the drop down
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                                            android.R.layout.simple_spinner_dropdown_item, cities);
        Spinner dropdown = findViewById(R.id.spinner1);
        dropdown.setAdapter(adapter);

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // Your code here
                String city = cities[i];
                setUpActivity(city);
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });


        final DatabaseReference firebasePledges = database.child("pledges");
        // Retrieve new posts as they are added to Firebase
        firebasePledges.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot item_snapshot : dataSnapshot.getChildren()) {
                    String curUserId = item_snapshot.getKey();
                    Map<String, Object> curPledge = (Map<String, Object>) item_snapshot.getValue();

                    String dietOption = String.valueOf(curPledge.get("dietOption"));
                    Double saveAmount = Double.valueOf(curPledge.get("saveAmount").toString());
                    String municipality = String.valueOf(curPledge.get("municipality"));

                    // current city
                    Pledge pledge = new Pledge(saveAmount, dietOption, municipality);
                    ArrayList<Pledge> pledgeList = pledges.get(municipality);
                    pledgeList.add(pledge);
                    pledges.put(municipality, pledgeList);

                    // for metro vancouver
                    if(!(municipality.equals("null")) && (!(municipality.equals("Undefined")))) {
                        ArrayList<Pledge> metroVancouverList = pledges.get("Metro Vancouver");
                        metroVancouverList.add(pledge);
                        pledges.put("Metro Vancouver", metroVancouverList);
                    }

                    userNames.put(curUserId, "");
                }

                Spinner dropdown = findViewById(R.id.spinner1);
                String city = dropdown.getSelectedItem().toString();

                for (final String key : userNames.keySet()) {
                    final DatabaseReference name = database.child("user").child(key).child("name");
                    name.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String name = dataSnapshot.getValue(String.class);
                            userNames.put(key, name);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    final DatabaseReference photo = database.child("user").child(key).child("icon");
                    photo.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String photo = dataSnapshot.getValue(String.class);
                            pictures.put(key, photo);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

                setUpActivity(city);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    protected void onStart() {
        super.onStart();
    }

    private void setUpActivity(String city) {
        BarChart chart = findViewById(R.id.chart);

        final List<String> xLabels = getLabels();
        List<BarEntry> entries = addEntries(city);

        float totalAmount = 0;
        for(int i = 0; i < entries.size(); i++) {
            BarEntry entry = entries.get(i);

            totalAmount += entry.getY();
        }


        ArrayList<Pledge> pledgeList = pledges.get(city);
        int totalPledges = pledgeList.size();
        Float average = (totalPledges == 0) ? 0 : totalAmount / totalPledges;

        TextView pledgeCount = findViewById(R.id.pledge_count);
        pledgeCount.setText(Integer.toString(totalPledges));

        TextView pledgeTotal = findViewById(R.id.pledge_total_save);
        pledgeTotal.setText(Float.toString(totalAmount));

        TextView pledgeAvg = findViewById(R.id.pledge_avg);
        pledgeAvg.setText(Float.toString(average));

        BarDataSet set = new BarDataSet(entries, "BarDataSet");
        set.setDrawValues(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if (value <= xLabels.size() - 1) {
                    return xLabels.get((int) value);
                }
                return "";
            }
        });

        BarData data = new BarData(set);
        data.setBarWidth(0.9f); // set custom bar width
        chart.setData(data);
        chart.setFitBars(true); // make the x-axis fit exactly all bars
        chart.getDescription().setEnabled(false);
        chart.getLegend().setEnabled(false);

        // remove yaxis
        YAxis yAxisRight = chart.getAxisRight();
        yAxisRight.setEnabled(false);
        YAxis yAxisLeft = chart.getAxisLeft();
        yAxisLeft.setEnabled(false);

        chart.invalidate(); // refresh
    }

    private Map<String,ArrayList> createMap(String[] cities) {
        Map<String, ArrayList> citiesMap = new HashMap<>();

        for(int i = 0; i < cities.length; i++) {
            ArrayList<Pledge> pledges = new ArrayList<>();
            String city = cities[i];
            citiesMap.put(city, pledges);
        }

        // no city in database
        ArrayList<Pledge> pledges = new ArrayList<>();
        citiesMap.put("null", pledges);

        return citiesMap;
    }

    private List<String> getLabels() {
        List<String> labels = new ArrayList<>();
        String[] mealOptions = {"Low Meat Option", "Meat Eater Option", "Plant Based Diet"};


        for(int i = 0; i < mealOptions.length; i++) {
            labels.add(mealOptions[i]);
        }

        return labels;
    }

    private List<BarEntry> addEntries(String city) {
        List<BarEntry> entries = new ArrayList<>();

        ArrayList<Pledge> arrayList = pledges.get(city);

        HashMap<String, Double> planCount = new HashMap<>();
        planCount.put("Low Meat Option", 0.0);
        planCount.put("Meat Eater Option", 0.0);
        planCount.put("Plant Based Diet", 0.0);

        for(int i = 0; i < arrayList.size(); i++) {
            Pledge curPledge = arrayList.get(i);

            Double count = planCount.get(curPledge.dietOption);
            count += curPledge.saveAmount;
            planCount.put(curPledge.dietOption, count);
        }

        float i = 0.0f;
        for (String key : planCount.keySet()) {
            Double count = planCount.get(key);

            BarEntry entry = new BarEntry(i, count.floatValue());
            entries.add(entry);
            i += 1;
        }

        return entries;
    }

    public void seePledgeSummary(View v) {
        Intent myIntent = new Intent(PledgeSummary.this, ViewAllPledges.class);

        Spinner dropdown = findViewById(R.id.spinner1);
        String city = dropdown.getSelectedItem().toString();

        myIntent.putExtra("municipality", city);
        myIntent.putExtra("usernames", userNames);
        myIntent.putExtra("pictures", pictures);

        startActivity(myIntent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.user_dashboard:
                if (user.isAnonymous()){
                    startActivity(new Intent(this,UserLogin.class));
                    return true;
                } else {
                    //  startActivity(new Intent(this, DashBoardActivity.class));
                    return true;
                }
            case R.id.view_all_pledge:
                startActivity(new Intent(this,ViewAllPledges.class));
                return true;
            case R.id.calculate_consumption:
                startActivity(new Intent(this,ConsumptionQuiz1.class));
                return true;
            case R.id.profile_login:
                if (user.isAnonymous()){
                    startActivity(new Intent(this,UserLogin.class));
                    return true;
                } else {
                    // startActivity(new Intent this, UserDashboard.class);
                    return true;
                }
            case R.id.about_us:
                startActivity(new Intent(this,AboutActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
