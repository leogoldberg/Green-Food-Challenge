package team6.cmpt276.greenfoodchallenge.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import java.util.List;

import team6.cmpt276.greenfoodchallenge.R;
import team6.cmpt276.greenfoodchallenge.classes.CardAdapter;
import team6.cmpt276.greenfoodchallenge.classes.MealInformation;

//import com.google.firebase.storage.StorageReference;

public class FragmentTwo extends Fragment {
    private RecyclerView recyclerView;
    List<MealInformation> data;

    private CardAdapter adapter;

    //filter this data so that it handles only those from current user
    final FirebaseDatabase database = FirebaseDatabase.getInstance();

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String userID = user.getUid();
    DatabaseReference ref = database.getInstance().getReference("meals");

    private int position;

    public FragmentTwo(){
        //this is meant to be kept empty
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            position = getArguments().getInt("position");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.activity_user_meals, container, false);

        data = new ArrayList<>();
        recyclerView = view.findViewById(R.id.userMealList);



        //recyclerView.setHasFixedSize(true);
        data = getData();



        //System.out.println("Fetched data:" + data.size());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;

    }

    private List<MealInformation> getData() {
        ref.orderByChild("userID").equalTo(userID).addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("We're done loading the initial "+dataSnapshot.getChildrenCount()+" items");
                adapter = new CardAdapter(getActivity(), data);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

            public void onCancelled(FirebaseError firebaseError) { }
        });
        ref.orderByChild("userID").equalTo(userID).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                MealInformation obj = dataSnapshot.getValue(MealInformation.class);
                data.add(obj);
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
