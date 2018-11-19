package team6.cmpt276.greenfoodchallenge.classes;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import team6.cmpt276.greenfoodchallenge.R;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.MyViewHolder>{

    private LayoutInflater inflater;
    List<MealInformation> data;
    public FeedAdapter(Context context, List<MealInformation>data){
        inflater = LayoutInflater.from(context);
        this.data = data;
        //System.out.println("FeedAdapter: data.size: " + data.size());
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //System.out.println("onCreateViewHolder called.............");
        View view = inflater.inflate(R.layout.custom_meal_row, viewGroup, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, int i) {
        //System.out.println("onBindViewHolder called.............");
       MealInformation current = data.get(i);
       viewHolder.mealName.setText(current.mealName);
       viewHolder.mealInfo.setText(current.mealDescription);
       viewHolder.proteinChoice.setText(current.protein);
       viewHolder.restaurantInfo.setText(current.address);
       viewHolder.restaurantName.setText(current.restaurantName);
       viewHolder.iconId.setImageResource(current.iconResource);
       viewHolder.starRating.setNumStars(current.rating);
    }

    @Override
    public int getItemCount() {
        //System.out.println("getItemCount called........... Datasize: " + data.size());
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView mealName;
        TextView proteinChoice;
        TextView mealInfo;
        RatingBar starRating;
        ImageView iconId;
        TextView restaurantName;
        TextView restaurantInfo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mealName = itemView.findViewById(R.id.mealName);
            proteinChoice = itemView.findViewById(R.id.proteinChoice);
            mealInfo = itemView.findViewById(R.id.mealDescription);
            restaurantName = itemView.findViewById(R.id.restaurantName);
            restaurantInfo = itemView.findViewById(R.id.restaurantInfo);
            iconId = itemView.findViewById(R.id.listIcon);
            starRating = itemView.findViewById(R.id.ratingBar);
        }
    }
}
