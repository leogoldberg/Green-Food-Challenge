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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import team6.cmpt276.greenfoodchallenge.R;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    static List<MealInformation> data;
    private Context userMealContext;
    private boolean userWants = false;

    private DatabaseReference database = FirebaseDatabase.getInstance().getReference("meals");
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private String userID = user.getUid();

    ArrayList<String> keyList = new ArrayList<String>();




    public CardAdapter(Context context, List<MealInformation> data) {
        inflater = LayoutInflater.from(context);
        userMealContext = context;
        this.data = data;



        //System.out.println("FeedAdapter: data.size: " + data.size());
    }

    public CardAdapter() {

    }

    @NonNull
    @Override
    public CardAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //System.out.println("onCreateViewHolder called.............");
        View view = inflater.inflate(R.layout.meal_card, viewGroup, false);
        CardAdapter.MyViewHolder holder = new CardAdapter.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CardAdapter.MyViewHolder viewHolder, final int position) {
        //System.out.println("onBindViewHolder called.............");
        MealInformation current = data.get(position);
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
        Glide.with(userMealContext).load(url).into(viewHolder.iconId);
        //viewHolder.iconId.setImageResource(current.iconResource);
        viewHolder.starRating.setNumStars(current.rating);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item_snapshot : dataSnapshot.getChildren()) {
                    final String key = item_snapshot.getKey();
                    keyList.add(key);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });

        viewHolder.garbage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //this has to be done after yes on popup

                //show suggest if the meal should really be deleted


              //  Intent askingUser = new Intent(view.getContext(), DeleteMealPopup.class);
              //  Bundle extras = new Bundle();
              //  extras.putInt("position", position);
              //  askingUser.putExtras(extras);
                //remove from recycle view but this works only temporarily(probably because remove on databse doesn't work

                data.remove(position);
                onItemRemoved(position);
                String keyToDeleteItem = keyList.get(position);
                database.child(keyToDeleteItem).removeValue();

             //   view.getContext().startActivity(askingUser);


            }
        });


    }

    public void onItemRemoved(int position){
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
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