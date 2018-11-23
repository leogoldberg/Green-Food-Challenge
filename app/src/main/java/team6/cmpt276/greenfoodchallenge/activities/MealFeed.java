package team6.cmpt276.greenfoodchallenge.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import team6.cmpt276.greenfoodchallenge.R;
import team6.cmpt276.greenfoodchallenge.classes.FeedAdapter;
import team6.cmpt276.greenfoodchallenge.classes.MealInformation;

public class MealFeed extends AppCompatActivity {

    private RecyclerView recyclerView;
    List<MealInformation> data;
    List<MealInformation> dataBackup;
    String filterCity;
    String filterProtein;

    private FeedAdapter mAdapter;

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference ref = database.getReference("meals");
    private boolean finishedLoading;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_feed);
        finishedLoading = false;
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Meals Feed");
        Spinner locationSpinner = (Spinner) findViewById(R.id.locationSpinner);
        Spinner mealSpinner = (Spinner) findViewById(R.id.proteinSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.protein_names, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mealSpinner.setAdapter(adapter);
        mealSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                if(finishedLoading==true) {
                    data.clear();
                    filterProtein = (String) parent.getItemAtPosition(pos);
                    if (dataBackup != null) { //leo:added this because was getting null error
                        for (int i = 0; i < dataBackup.size(); i++) {
                            MealInformation item = dataBackup.get(i);
                            if ((item.protein.equals(filterProtein) || filterProtein.equals("All")) && item.city.equals(filterCity)) {
                                //System.out.println("adding an item with " + item.protein);
                                data.add(item);
                            }
                        }
                        runOnUiThread(new Runnable() {
                            public void run() {
                                mAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
        adapter = ArrayAdapter.createFromResource(this,
                R.array.location_names, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSpinner.setAdapter(adapter);
        locationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                // An item was selected. You can retrieve the selected item using
                if(finishedLoading==true) {
                    data.clear();
                    filterCity = (String) parent.getItemAtPosition(pos);
                    if (dataBackup != null) {
                        for (int i = 0; i < dataBackup.size(); i++) {
                            MealInformation item = dataBackup.get(i);
                            if (item.city.equals(filterCity) && (item.protein.equals(filterProtein) || filterProtein.equals("All"))) {
                                //System.out.println("adding an item from " + item.city);
                                data.add(item);
                            }/*else {
                              System.out.println("ignoring item from " + item.city);
                          }*/
                        }
                        runOnUiThread(new Runnable() {
                            public void run() {
                                mAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

        data = new ArrayList<>();
        recyclerView = findViewById(R.id.mealFeedList);
        //recyclerView.setHasFixedSize(true);
        data = getData();
        //System.out.println("Fetched data:" + data.size());
        recyclerView.setLayoutManager(new LinearLayoutManager(MealFeed.this));

        bottomNavigationView =findViewById(R.id.navbar);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    //add case feed after feed activity pushed
                    case R.id.action_feed:
                        if (user.isAnonymous()){
                            startActivity(new Intent(bottomNavigationView.getContext(),UserLogin.class));
                            return true;
                        } else {
                            startActivity(new Intent(bottomNavigationView.getContext(), MealFeed.class));;
                            return true;
                        }
                    case R.id.view_all_pledge:
                        if (user.isAnonymous()){
                            startActivity(new Intent(bottomNavigationView.getContext(),UserLogin.class));
                            return true;
                        } else {
                            startActivity(new Intent(bottomNavigationView.getContext(),PledgeSummary.class));
                            return true;
                        }
                    case R.id.calculate_consumption:
                        startActivity(new Intent(bottomNavigationView.getContext(),ConsumptionQuiz1.class));
                        return true;
                    case R.id.about:
                        startActivity(new Intent(bottomNavigationView.getContext(),AboutActivity.class));
                        return true;
                    case R.id.profile:
                        if (user.isAnonymous()){
                            startActivity(new Intent(bottomNavigationView.getContext(),UserLogin.class));
                            return true;
                        } else {
                            startActivity(new Intent (bottomNavigationView.getContext(), ProfileTab.class));
                            return true;
                        }
                    default:
                        return false;
                }
            }
        });
    }

    public List<MealInformation> getData(){
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("We're done loading the initial "+dataSnapshot.getChildrenCount()+" items");
                mAdapter = new FeedAdapter(MealFeed.this, data);
                recyclerView.setAdapter(mAdapter);
                finishedLoading=true;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

            public void onCancelled(FirebaseError firebaseError) { }
        });
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                MealInformation obj = dataSnapshot.getValue(MealInformation.class);
                data.add(obj);
                dataBackup = new ArrayList<>(data);
                //System.out.println(obj.mealName);
                //System.out.println("DATA size:" + data.size());
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return data;


    }
}
