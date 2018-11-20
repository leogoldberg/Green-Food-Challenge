package team6.cmpt276.greenfoodchallenge.activities;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.firebase.FirebaseError;
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

    private FeedAdapter mAdapter;

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("meals");
    private boolean finishedLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_feed);
        finishedLoading = false;
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Meals Feed");
        Spinner spinner = (Spinner) findViewById(R.id.locationSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.location_names, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

              public void onItemSelected(AdapterView<?> parent, View view,
                                         int pos, long id) {
                  // An item was selected. You can retrieve the selected item using
                  if(finishedLoading==true) {
                      //System.out.println("Selected Item: " + parent.getItemAtPosition(pos));
                      data.clear();
                      for(int i=0;i<dataBackup.size();i++){
                          MealInformation item = dataBackup.get(i);
                          if(item.city.equals(parent.getItemAtPosition(pos))) {
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