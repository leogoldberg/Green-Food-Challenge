package team6.cmpt276.greenfoodchallenge.classes;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import team6.cmpt276.greenfoodchallenge.R;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    static List<MealInformation> data;
    private Context context;

    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    ArrayList<String> keyList = new ArrayList<>();

    public CardAdapter(Context context, List<MealInformation> data, ArrayList<String> keyList) {
        //initialize
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.data = data;
        this.keyList = keyList;
    }

    public CardAdapter() {
    }

    @NonNull
    @Override
    public CardAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.meal_card, viewGroup, false);
        CardAdapter.MyViewHolder holder = new CardAdapter.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CardAdapter.MyViewHolder viewHolder, final int position) {
        MealInformation current = data.get(position);
        viewHolder.mealName.setText(current.mealName);
        viewHolder.mealInfo.setText(current.mealDescription);
        viewHolder.proteinChoice.setText(current.protein);
        viewHolder.restaurantInfo.setText(current.address);
        viewHolder.restaurantName.setText(current.restaurantName);
        String url = "https://firebasestorage.googleapis.com/v0/b/greenfoodchallenge-9ec3c.appspot.com/o/meals%2Fnoimage.jpg?alt=media&token=30584887-c1cd-437a-bc84-ac337358dc90";
        if(current.fileName != null){
            url= "https://firebasestorage.googleapis.com/v0/b/greenfoodchallenge-9ec3c.appspot.com/o/meals%2F" + current.userID + "%2F" + current.fileName + "?alt=media&token=30584887-c1cd-437a-bc84-ac337358dc90";
        }
        Glide.with(context).load(url).into(viewHolder.iconId);
        viewHolder.starRating.setNumStars(current.rating);


        viewHolder.garbage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //remove data from database and from recyclerView
                String keyToDeleteItem = keyList.get(position);
                database.child("meals").child(keyToDeleteItem).removeValue();
                data.remove(position);
                onItemRemoved(position);


            }
        });


    }

    public void onItemRemoved(int position){
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
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
        ImageButton garbage;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mealName = itemView.findViewById(R.id.meal_name);
            proteinChoice = itemView.findViewById(R.id.protein_option);
            mealInfo = itemView.findViewById(R.id.meal_description);
            restaurantName = itemView.findViewById(R.id.restaurant_name);
            restaurantInfo = itemView.findViewById(R.id.address);
            iconId = itemView.findViewById(R.id.meal_photo);
            starRating = itemView.findViewById(R.id.rating_bar);
            garbage = itemView.findViewById(R.id.garbage_button);
            garbage.setLayoutParams(new LinearLayout.LayoutParams(100,100));
        }
    }

}