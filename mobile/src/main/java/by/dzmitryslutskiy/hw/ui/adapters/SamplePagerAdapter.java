package by.dzmitryslutskiy.hw.ui.adapters;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.ActionBar;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import by.dzmitryslutskiy.hw.bo.Countries;
import by.dzmitryslutskiy.hw.ui.fragments.CountryFragment;

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

    public static final String KEY_COUNTRIES = "mCountries";
    public static final String KEY_LIST_SIZE = "mStringList.listSize";
    public static final String KEY_STRING_LIST = "mStringList";

    List<String> mStringList;
    Countries mCountries;

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
        return mStringList.size();
    }

    @Override
    public Fragment getItem(int i) {
        Log.d(TAG, "getItem:" + i);
        CountryFragment fragment = new CountryFragment();
        if (mCountries != null) {
            fragment.setListCountry(mCountries);
        }
        return fragment;
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
            notifyDataSetChanged();
            mActionBar.addTab(mActionBar.newTab()
                    .setText("Tab " + (mActionBar.getTabCount() + 1))
                    .setTabListener(mTabListener));

            return true;
        }

        return false;
    }

    public void updateCountryList(Countries countries) {
        mCountries = countries;
    }

    @Override
    public Parcelable saveState() {
        Parcelable parcelable = super.saveState();

        if (parcelable != null && parcelable instanceof Bundle) {
            Bundle bundle = (Bundle) parcelable;
            bundle.putParcelable(KEY_COUNTRIES, mCountries);
            bundle.putInt(KEY_LIST_SIZE, mStringList == null ? 0 : mStringList.size());

            for (int i = 0; i < mStringList.size(); i++) {
                bundle.putString(KEY_STRING_LIST + i, mStringList.get(i));
            }
        }

        return parcelable;
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
        super.restoreState(state, loader);

        if (state != null && state instanceof Bundle) {
            Bundle bundle = (Bundle) state;

            mCountries = bundle.getParcelable(KEY_COUNTRIES);
            int size = bundle.getInt(KEY_LIST_SIZE);
            mStringList = new ArrayList<String>();
            if (size > 0) {
                for (int i = 0; i < size; i++) {
                    add(bundle.getString(KEY_STRING_LIST + i));
                }
            }
        }
    }
}
