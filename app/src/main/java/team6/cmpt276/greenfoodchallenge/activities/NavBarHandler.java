package team6.cmpt276.greenfoodchallenge.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseUser;

import team6.cmpt276.greenfoodchallenge.R;
import team6.cmpt276.greenfoodchallenge.activities.AboutActivity;
import team6.cmpt276.greenfoodchallenge.activities.ConsumptionQuiz1;
import team6.cmpt276.greenfoodchallenge.activities.MealFeed;
import team6.cmpt276.greenfoodchallenge.activities.PledgeSummary;
import team6.cmpt276.greenfoodchallenge.activities.UserLogin;
import team6.cmpt276.greenfoodchallenge.activities.UserProfile;



public abstract class NavBarHandler extends AppCompatActivity{
    private Activity activity;
    private FirebaseUser user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
    }

    NavBarHandler(Activity activity, FirebaseUser user){
        this.activity=activity;
        this.user=user;
    }

    public NavBarHandler(){
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_feed:
                    activity.startActivity(new Intent(this.activity, MealFeed.class));
                    return true;
                case R.id.view_all_pledge:
                    activity.startActivity(new Intent(this.activity,PledgeSummary.class));
                    return true;
                case R.id.calculate_consumption:
                    activity.startActivity(new Intent(this.activity,ConsumptionQuiz1.class));
                    return true;
                case R.id.about:
                    activity.startActivity(new Intent(this.activity,AboutActivity.class));
                case R.id.profile:
                    if (user.isAnonymous()){
                        activity.startActivity(new Intent(this.activity,UserLogin.class));
                        return true;
                    } else {
                        activity.startActivity(new Intent (this.activity, UserProfile.class));
                        return true;
                    }
                default:
                    return false;
            }
        }


    protected abstract int getContentView();

}


