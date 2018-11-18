package team6.cmpt276.greenfoodchallenge.activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Collections;
import java.util.List;

import team6.cmpt276.greenfoodchallenge.R;
import team6.cmpt276.greenfoodchallenge.classes.FeedAdapter;
import team6.cmpt276.greenfoodchallenge.classes.MealInformation;

public class MealFeed extends AppCompatActivity {

    private RecyclerView recyclerView;
    List<MealInformation> data;

    private FeedAdapter adapter;

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("meals");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_feed);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Meals Feed");
        List<MealInformation> data = Collections.emptyList();
        recyclerView = findViewById(R.id.mealFeedList);
        adapter = new FeedAdapter(MealFeed.this, getData());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MealFeed.this));
    }

    public List<MealInformation> getData(){
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                MealInformation obj = dataSnapshot.getValue(MealInformation.class);
                data.add(obj);
                System.out.println(obj);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return data;
    }
}
