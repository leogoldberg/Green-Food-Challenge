package team6.cmpt276.greenfoodchallenge.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import team6.cmpt276.greenfoodchallenge.R;
import team6.cmpt276.greenfoodchallenge.classes.Pledge;
import team6.cmpt276.greenfoodchallenge.classes.UserData;

public class ResultActivity2 extends AppCompatActivity {
    private UserData suggestedConsumption;
    private UserData currentConsumption;
    private double saved;
    private String dietOption;

    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private String userID = user.getUid();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result2);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Your Suggested Diet");

        Drawable threeLineIcon = ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_dehaze_black_24dp);
        toolbar.setOverflowIcon(threeLineIcon);

        Intent intent = getIntent();
        dietOption = intent.getStringExtra("dietOption");

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentConsumption = dataSnapshot.child("current_consumptions").child(userID).getValue(UserData.class);
                suggestedConsumption = dataSnapshot.child("suggested_consumptions").child(userID).getValue(UserData.class);
                int total_amount_per_week = suggestedConsumption.getTotalFrequency();

                double totalCurrentConsumption = currentConsumption.getTotalco2perYear();
                double totalSuggestedConsumption = suggestedConsumption.getTotalco2perYear();
                saved = totalCurrentConsumption - totalSuggestedConsumption;

                if(saved < 0) {
                    saved = 0;
                }

                // change text
                TextView amountSaved = findViewById(R.id.amount_saved);
                amountSaved.setText((Math.round(saved) / 1000) + "kg CO2e ");

                TextView calculateTreesSaved = findViewById(R.id.amount_tree_saved);
                calculateTreesSaved.setText("Or " + calculateTreesSaved(saved) + " trees per year");

                TextView vancouverSaved = findViewById(R.id.vancouverSaved);
                vancouverSaved.setText(calculateVancouverSaved(saved) + " tons C02e");

                // sets up the pie charts
                PieChart chart = initializePieChart(R.id.chart);
                List<PieEntry> entries = addEntries(suggestedConsumption, total_amount_per_week);
                PieDataSet dataSet = setPieDataSet(entries);

                PieData pieData = setPieData(dataSet);

                chart.setData(pieData);
                chart.invalidate();

                Button redoQuiz = findViewById(R.id.redoQuiz);
                redoQuiz.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ResultActivity2.this, ConsumptionQuiz1.class);
                        startActivity(intent);
                    }
                });



                Button pledgeButton = findViewById(R.id.pledgeButton);
                pledgeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Pledge userPledge = new Pledge(Math.round(saved),dietOption);
                        database.child("pledges").child(userID).setValue(userPledge);

                        if (user.isAnonymous()){
                            Intent intent = new Intent(ResultActivity2.this, UserLogin.class);
                            startActivity(intent);
                        }
                        else{
                            Intent intent = new Intent(ResultActivity2.this, UserDashboard.class);
                            startActivity(intent);
                        }

                    }
                });

                // On login sidebar or make a pledge button click: Start activity UserLogin
                // Intent intent = new Intent(UserLogin.this, Dashboard.class);
                //startActivity(intent);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    /**
     * Add entries based on the user input
     * @param suggestedConsumption        an object that holds all the user inputs
     * @param total_amount_per_week     the total frequency they eat protein products
     * @return the list of entries to show on the pie chart
     */
    private List<PieEntry> addEntries(UserData suggestedConsumption,
                                      int total_amount_per_week) {
        List<PieEntry> entries = new ArrayList<>();

        ArrayList<String> foodNames = suggestedConsumption.getFoodNames();
        ArrayList<Integer> userFoodData = suggestedConsumption.getUserFoodData();

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
     * Calculates the tree saved
     * @param saved     the amount of co2e saved
     * @return          returns the number of trees saved
     */
    private long calculateTreesSaved(double saved) {
        return Math.round(saved / 21.7);
    }

    /**
     * Calculates the amount vancouver saved
     * @param saved     the amount of co2e saved
     * @return          returns the number of trees saved
     */
    private long calculateVancouverSaved(double saved) {
        return Math.round(saved / 500);
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
                    startActivity(new Intent(this, UserDashboard.class));
                    return true;
                }
            case R.id.view_all_pledge:
                startActivity(new Intent(this,PledgeSummary.class));
                return true;
            case R.id.calculate_consumption:
                startActivity(new Intent(this,ConsumptionQuiz1.class));
                return true;
            case R.id.profile_login:
                if (user.isAnonymous()){
                    startActivity(new Intent(this,UserLogin.class));
                    return true;
                } else {
                    startActivity(new Intent (this, UserProfile.class));
                    return true;
                }
            case R.id.about_us:
                startActivity(new Intent(this,AboutActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
