package team6.cmpt276.greenfoodchallenge.activities;

        import android.content.Intent;
        import android.os.Bundle;
        import android.support.annotation.NonNull;
        import android.support.v4.app.Fragment;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
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
        import team6.cmpt276.greenfoodchallenge.classes.UserData;

public class UserPledge extends Fragment {
    public UserPledge(){
        // meant to be kept empty
    }

    private UserData suggestedConsumption;
    private UserData currentConsumption;
    private double saved;
    private TextView amountSaved;
    private TextView planView;
    private Button shareButton;

    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private String userID = user.getUid();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.activity_user_pledge, container, false);


        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                currentConsumption = dataSnapshot.child("current_consumptions").child(userID).getValue(UserData.class);
                suggestedConsumption = dataSnapshot.child("suggested_consumptions").child(userID).getValue(UserData.class);
                int total_amount_per_week_current = currentConsumption.getTotalFrequency();
                int total_amount_per_week_suggested = suggestedConsumption.getTotalFrequency();

                saved = currentConsumption.getTotalco2perYear() - suggestedConsumption.getTotalco2perYear();

                if(saved < 0) {
                    saved = 0;
                }

                // sets up the pie charts
                PieChart suggChart = initializePieChart(R.id.suggChart);
                List<PieEntry> entries = addEntries(suggestedConsumption, total_amount_per_week_suggested);
                PieDataSet dataSet = setPieDataSet(entries);

                PieData pieData = setPieData(dataSet);

                suggChart.setData(pieData);
                suggChart.invalidate();

                // set up current diet chart
                PieChart currChart = initializePieChart(R.id.currChart);
                List<PieEntry> currEntries = addEntries(currentConsumption, total_amount_per_week_current);
                PieDataSet currDataSet = setPieDataSet(currEntries);

                PieData currPieData = setPieData(currDataSet);

                currChart.setData(currPieData);
                currChart.invalidate();



                // set amount saved text
                amountSaved = view.findViewById(R.id.saveText);
                planView=view.findViewById(R.id.dietText);

                String city = dataSnapshot.child("pledges").child(userID).child("municipality").getValue(String.class);
                String diet = dataSnapshot.child("pledges").child(userID).child("dietOption").getValue(String.class);

                if(diet==null){
                    planView.setText("None");
                    amountSaved.setText("None");
                }
                else{
                    planView.setText(diet);
                    amountSaved.setText(Math.round(saved)/1000 + "kg CO2e ");
                }



                final String dietPlanToSharing = diet;
                final String savedCO2eToSharing = amountSaved.getText().toString();

                shareButton = view.findViewById(R.id.shareButton);
                if(diet==null){
                    shareButton.setVisibility(View.INVISIBLE);
                }

                else {
                    shareButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(getActivity(), SharingActivity.class);
                            Bundle extras = new Bundle();
                            extras.putString("dietPlanName", dietPlanToSharing);
                            extras.putString("savedAmount", savedCO2eToSharing);
                            intent.putExtras(extras);

                            startActivity(intent);

                        }
                    });
                }

                Button deletePledge = view.findViewById(R.id.deletePledge);
                if(diet==null){
                    deletePledge.setVisibility(View.INVISIBLE);
                }

                else {

                    deletePledge.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            database.child("pledges").child(userID).removeValue();
                            amountSaved.setText("0 CO2e ");
                            shareButton.setVisibility(View.INVISIBLE);
                        }
                    });
                }


                Button changePlanButton = view.findViewById(R.id.changeButton);

                changePlanButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), PlannerQuiz.class);
                        startActivity(intent);
                    }
                });


                // On login sidebar or make a pledge button click: Start activity UserLogin
                // Intent intent = new Intent(UserLogin.this, Dashboard.class);
                //startActivity(intent);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
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
                PieChart chart = view.findViewById(id);
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


        });

        return view;

    }


}
