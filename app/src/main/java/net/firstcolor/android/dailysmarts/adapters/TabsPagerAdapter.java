package net.firstcolor.android.dailysmarts.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import net.firstcolor.android.dailysmarts.MainActivity;
import net.firstcolor.android.dailysmarts.QuotesFragment;
import net.firstcolor.android.dailysmarts.R;
import net.firstcolor.android.dailysmarts.services.DBQuoteProvider;
import net.firstcolor.android.dailysmarts.services.DummyQuoteProvider;
import net.firstcolor.android.dailysmarts.services.WebQuoteProvider;

public class TabsPagerAdapter extends FragmentPagerAdapter {

    private QuotesFragment dailyQuoteTabFragment;
    private QuotesFragment myQuotesTabFragment;

    public TabsPagerAdapter(FragmentManager fm, MainActivity activity) {
        super(fm);
        dailyQuoteTabFragment = QuotesFragment.newInstance(new WebQuoteProvider());
        //myQuotesTabFragment = QuotesFragment.newInstance(new DummyQuoteProvider());
        myQuotesTabFragment = QuotesFragment.newInstance(new DBQuoteProvider());
        activity.setFragmentFavourites(myQuotesTabFragment);
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            return dailyQuoteTabFragment;
        }
        else if(position == 1){
            return myQuotesTabFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
