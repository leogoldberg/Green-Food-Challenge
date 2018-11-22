package team6.cmpt276.greenfoodchallenge.classes;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import team6.cmpt276.greenfoodchallenge.R;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.MyViewHolder>{
    private Context mContext;
    private String TAG = "FeedAdapter";
    private LayoutInflater inflater;
    List<MealInformation> data;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();
    public FeedAdapter(Context context, List<MealInformation>data){
        inflater = LayoutInflater.from(context);
        mContext = context;
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
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, final int position) {
        //System.out.println("onBindViewHolder called.............");
        final MealInformation current = data.get(position);
       viewHolder.mealName.setText(current.mealName);
       viewHolder.mealInfo.setText(current.mealDescription);
       viewHolder.proteinChoice.setText(current.protein);
       viewHolder.restaurantInfo.setText(current.address);
       viewHolder.restaurantName.setText(current.restaurantName);
       //System.out.println("current.fileName: " +current.fileName);
       String url = "https://firebasestorage.googleapis.com/v0/b/greenfoodchallenge-9ec3c.appspot.com/o/meals%2Fnoimage.jpg?alt=media&token=30584887-c1cd-437a-bc84-ac337358dc90";
       if(current.fileName != null){
            url= "https://firebasestorage.googleapis.com/v0/b/greenfoodchallenge-9ec3c.appspot.com/o/meals%2F" + current.fileName + "?alt=media&token=30584887-c1cd-437a-bc84-ac337358dc90";
       }
       Glide.with(mContext).load(url).into(viewHolder.iconId);
       //viewHolder.iconId.setImageResource(current.iconResource);
       viewHolder.starRating.setNumStars(current.rating);
       /*viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on: " + current.mealName);

                Toast.makeText(mContext, current.mealName, Toast.LENGTH_SHORT).show();

                //Intent intent = new Intent(mContext, ViewMeal.class);
                //intent.putExtra("mealId", position);
                //mContext.startActivity(intent);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        //System.out.println("getItemCount called........... Datasize: " + data.size());
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mealName;
        TextView proteinChoice;
        TextView mealInfo;
        RatingBar starRating;
        ImageView iconId;
        TextView restaurantName;
        TextView restaurantInfo;
        LinearLayout parentLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mealName = itemView.findViewById(R.id.mealName);
            proteinChoice = itemView.findViewById(R.id.proteinChoice);
            mealInfo = itemView.findViewById(R.id.mealDescription);
            restaurantName = itemView.findViewById(R.id.restaurantName);
            restaurantInfo = itemView.findViewById(R.id.restaurantInfo);
            iconId = itemView.findViewById(R.id.listIcon);
            starRating = itemView.findViewById(R.id.ratingBar);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
