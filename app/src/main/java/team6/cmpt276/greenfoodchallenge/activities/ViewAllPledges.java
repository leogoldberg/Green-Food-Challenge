
package team6.cmpt276.greenfoodchallenge.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.model.TableColumnWeightModel;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;
import team6.cmpt276.greenfoodchallenge.R;
import team6.cmpt276.greenfoodchallenge.classes.Pledge;
import team6.cmpt276.greenfoodchallenge.classes.PledgesAdapater;
import team6.cmpt276.greenfoodchallenge.classes.UserData;

// https://github.com/ISchwarz23/SortableTableView
// https://github.com/ISchwarz23/SortableTableView-ExampleApp/blob/master/app/src/main/java/com/sortabletableview/recyclerview/exampleapp/customdata/CustomDataExampleFragment.java
public class ViewAllPledges extends AppCompatActivity {
    //private static final String[] TABLE_HEADERS = { "This", "is", "a" };

    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private String userID = user.getUid();
    private Map<String, ArrayList> pledges;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_pledges);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("View All Pledges");

        Intent intent = getIntent();
        String city = intent.getStringExtra("municipality");

        final String[] cities = {   "Richmond", "Coquitlam", "Surrey", "Vancouver",
                                    "New Westminister", "Burnaby"};

        // set the hashmap
        pledges = createMap(cities);

        // set the drop down
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                                            android.R.layout.simple_spinner_dropdown_item, cities);
        Spinner dropdown = findViewById(R.id.spinner1);
        dropdown.setAdapter(adapter);

        int spinnerPosition = adapter.getPosition(city);
        dropdown.setSelection(spinnerPosition);

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // Your code here
                String city = cities[i];
                setUpTableView(city);
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
                    Map<String, Object> curPledge = (Map<String, Object>) item_snapshot.getValue();

                    String dietOption = String.valueOf(curPledge.get("dietOption"));
                    Double saveAmount = Double.valueOf(curPledge.get("saveAmount").toString());
                    String municipality = String.valueOf(curPledge.get("municipality"));

                    Pledge pledge = new Pledge(saveAmount, dietOption, municipality);
                    ArrayList<Pledge> pledgeList = pledges.get(municipality);
                    pledgeList.add(pledge);

                    pledges.put(municipality, pledgeList);
                }

                Spinner dropdown = findViewById(R.id.spinner1);
                String city = dropdown.getSelectedItem().toString();

                setUpTableView(city);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void setUpTableView(String city) {
        final int COLUMN_COUNT = 3;

        TableView tableView = findViewById(R.id.tableView);
        tableView.setHeaderVisible(false);

        TableColumnWeightModel columnModel = new TableColumnWeightModel(COLUMN_COUNT);
        columnModel.setColumnWeight(0, 2);
        columnModel.setColumnWeight(1, 4);
        columnModel.setColumnWeight(2, 2);
        tableView.setColumnModel(columnModel);


        ArrayList<Pledge> pledgeList = pledges.get(city);

        String[][] data = new String[pledgeList.size()][COLUMN_COUNT];
        for(int i = 0 ; i < pledgeList.size(); i++) {
            Pledge curPledge = pledgeList.get(i);

            data[i][0] = "PICTURE";
            data[i][1] = "NAME: \n" + System.getProperty("line.separator") + curPledge.dietOption;
            data[i][2] = roundOffTo2DecPlaces((float) curPledge.saveAmount / 1000000) + " tons";
        }

        //tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(this, TABLE_HEADERS));
        tableView.setDataAdapter(new PledgesAdapater(this, data));
    }

    private Map<String,ArrayList> createMap(String[] cities) {
        Map<String, ArrayList> citiesMap = new HashMap<>();

        for(int i = 0; i < cities.length; i++) {
            ArrayList<Pledge> pledges = new ArrayList<Pledge>();
            String city = cities[i];
            citiesMap.put(city, pledges);
        }

        // no city in database
        ArrayList<Pledge> pledges = new ArrayList<>();
        citiesMap.put("null", pledges);

        return citiesMap;
    }

    private static class UserDataComparator implements Comparator<UserData> {
        @Override
        public int compare(UserData car1, UserData car2) {
            //return car1.getProducer().getName().compareTo(car2.getProducer().getName());
            return 1;
        }
    }

    private String roundOffTo2DecPlaces(float val) {
        return String.format("%.2f", val);
    }
}

