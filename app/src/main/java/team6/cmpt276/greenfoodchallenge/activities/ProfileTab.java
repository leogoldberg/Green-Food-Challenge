package team6.cmpt276.greenfoodchallenge.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import team6.cmpt276.greenfoodchallenge.R;
import team6.cmpt276.greenfoodchallenge.classes.ViewPagerAdapter;

public class ProfileTab extends AppCompatActivity {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_tab);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Green Food Challenge");

        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new FragmentOne(), "Pledge");
        adapter.addFragment(new FragmentTwo(), "Meal");
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);

    }



}
