package team6.cmpt276.greenfoodchallenge.classes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import team6.cmpt276.greenfoodchallenge.R;
import team6.cmpt276.greenfoodchallenge.activities.FragmentTwo;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.MyViewHolder> {

    private Context mContext;
    private List<Meal> mMealList;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView mMealName;
        public TextView mProteinOption;
        public TextView mMealDescription;
        public TextView mRestaurantName;
        public TextView mAddress;
        public RatingBar mStarRating;
        public ImageView mGarbage;
        public ImageView mPhoto;

        public MyViewHolder(View view){
            super(view);
            mMealName = (TextView) view.findViewById(R.id.meal_name);
            mProteinOption = (TextView) view.findViewById(R.id.protein_choice);
            mMealDescription = (TextView) view.findViewById(R.id.meal_description);
            mRestaurantName = (TextView) view.findViewById(R.id.restaurant_name);
            mAddress = (TextView) view.findViewById(R.id.address);
            mStarRating = (RatingBar) view.findViewById(R.id.rating_bar);
            mGarbage = (ImageView) view.findViewById(R.id.garbage);
            mPhoto = (ImageView) view.findViewById(R.id.meal_photo);

        }
    }

    public CardAdapter(FragmentTwo mContext, List<Meal> mealList){
        this.mContext = mContext;
        this.mMealList = mealList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_user_meals, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position){
        Meal meal = mMealList.get(position);
        holder.mMealName.setText(meal.getMealDescription());
        holder.mProteinOption.setText(meal.getProtein());
        holder.mMealDescription.setText(meal.getMealDescription());
        holder.mRestaurantName.setText(meal.getRestaurantName());
        holder.mAddress.setText(meal.getAddress());
        holder.mStarRating.setRating(meal.getRating());

        Glide.with(mContext).load(meal.getFileName()).into(holder.mPhoto);

        holder.mGarbage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //pop up to ask if user wants to delete post
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMealList.size();
    }

}
