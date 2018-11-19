package team6.cmpt276.greenfoodchallenge.activities;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import team6.cmpt276.greenfoodchallenge.R;
import team6.cmpt276.greenfoodchallenge.classes.CardAdapter;
import team6.cmpt276.greenfoodchallenge.classes.GridSpacingItemDecoration;
import team6.cmpt276.greenfoodchallenge.classes.Meal;

//import com.google.firebase.storage.StorageReference;

public class FragmentTwo extends Fragment {

    //to read and write data from the database
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private String userID = user.getUid();

    private RecyclerView mRecyclerView;
    private CardAdapter mCardAdapter;
    private List<Meal> mMealList;

    private Map<String, ArrayList> meals;


    public FragmentTwo(){
        //this is meant to be kept empty
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.activity_user_meals, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        mMealList = new ArrayList<>();
        mCardAdapter = new CardAdapter(this, mMealList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mCardAdapter);

        prepareMeals();


        return view;

    }

    private void prepareMeals() {
       //int list of image ids
       //set up list of  meals

        //pull each meal and make a meal variable and add them all to mealList
        //Meal one = pull a meal from data

        mCardAdapter.notifyDataSetChanged();

    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }


}
