package by.dzmitryslutskiy.hw.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.ActionBar;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import by.dzmitryslutskiy.hw.ui.fragments.SampleFragment;

/**
 * SamplePagerAdapter
 * Version information
 * 11.11.2014
 * Created by Dzmitry Slutskiy.
 */
public class SamplePagerAdapter extends FragmentStatePagerAdapter {
    public static final String TAG = SamplePagerAdapter.class.getSimpleName();
    private static final int MAX_COUNT = 150;
    public static final int MIN_COUNT = 5;

    List<String> mStringList;
    ActionBar mActionBar;
    ActionBar.TabListener mTabListener;

    public SamplePagerAdapter(final ActionBar actionBar, ActionBar.TabListener tabListener, FragmentManager fragmentManager) {
        super(fragmentManager);

        mStringList = new ArrayList<String>();
        mActionBar = actionBar;
        mTabListener = tabListener;

        Log.d(TAG, "SamplePagerAdapter.constructor");
    }

    @Override
    public int getCount() {
//        Log.d(TAG, "getCount:" + mStringList.size());
        return mStringList.size();
    }

    @Override
    public Fragment getItem(int i) {
        Log.d(TAG, "getItem:" + i);
        if (mStringList.size() > i) {
            Fragment fragment = new SampleFragment();
            fragment.setArguments(SampleFragment.prepareBundle(mStringList.get(i)));

            return fragment;
        } else {
            throw new IllegalArgumentException("Size of elements: " + mStringList.size() + " required: " + i);
        }
    }


    @Override
    public CharSequence getPageTitle(int position) {
        Log.d(TAG, "getPageTitle:" + position);
        return "OBJECT " + (position + 1);
    }

    public boolean add(String text) {
        Log.d(TAG, "add:" + text);
        if (mStringList.size() < MAX_COUNT) {
            mStringList.add(text);

            mActionBar.addTab(mActionBar.newTab()
                    .setText("Tab " + (mActionBar.getTabCount() + 1))
                    .setTabListener(mTabListener));

            return true;
        }

        return false;
    }
}
