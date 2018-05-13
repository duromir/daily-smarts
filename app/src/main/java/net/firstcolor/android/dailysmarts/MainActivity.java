package net.firstcolor.android.dailysmarts;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import net.firstcolor.android.dailysmarts.adapters.TabsPagerAdapter;

public class MainActivity extends AppCompatActivity implements QuotesFragment.OnFragmentInteractionListener{

    private TabsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private Toolbar toolbar;



    private QuotesFragment fragmentFavourites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initApp();
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupTabs();
    }

    public void setFragmentFavourites(QuotesFragment fragmentFavourites) {
        this.fragmentFavourites = fragmentFavourites;
    }

    private void setupTabs(){
        mSectionsPagerAdapter = new TabsPagerAdapter(getSupportFragmentManager(), this);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.app_tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0){
                    toolbar.findViewById(R.id.update_btn).setVisibility(View.VISIBLE);
                }
                else if(tab.getPosition() == 1){
                    toolbar.findViewById(R.id.update_btn).setVisibility(View.GONE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });
    }

    private void initApp(){
        App.appContext = getApplicationContext();
    }

    public void refreshFavourites(){
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                fragmentFavourites.loadItems();
            }
        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        //uri.
    }
}
