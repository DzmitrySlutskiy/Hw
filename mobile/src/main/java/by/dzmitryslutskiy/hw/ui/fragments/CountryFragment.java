package by.dzmitryslutskiy.hw.ui.fragments;


import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import by.dzmitryslutskiy.hw.R;
import by.dzmitryslutskiy.hw.bo.Countries;
import by.dzmitryslutskiy.hw.ui.adapters.CountryAdapter;

/**
 * CountryFragment
 * Version information
 * 11.11.2014
 * Created by Dzmitry Slutskiy.
 */
public class CountryFragment extends Fragment {
    public static final String TAG = CountryFragment.class.getSimpleName();

    public static final String FIRST_VISIBLE_POSITION = "FirstVisiblePosition";
    public static final String FIRST_VISIBLE_POSITION_TOP = "FirstVisiblePositionTop";

    public static int COUNTER = 0;
    public static final Object mCounterLocker = new Object();

    public static final String ARG_COUNTRY = "argText";

    private Countries mCountries;
    //  UI
    private ListView mListView;
    private int mLastVisibleIndex;
    private int mOffsetTop;

    private ProgressBar mProgressBar;

    public CountryFragment() {
        synchronized (mCounterLocker) {
            COUNTER++;
            Log.d(TAG, "count: " + COUNTER);
        }
    }

    /*@Override
    protected void finalize() throws Throwable {
        super.finalize();
        synchronized (mCounterLocker) {
            COUNTER--;
            Log.d(TAG, "finalize.count: " + COUNTER);
        }
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            Parcelable parcelable = bundle.getParcelable(ARG_COUNTRY);
            if (parcelable instanceof Countries) {
                mCountries = (Countries) parcelable;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_country, container, false);

        mListView = (ListView) view.findViewById(android.R.id.list);
        mProgressBar = (ProgressBar) view.findViewById(android.R.id.progress);
        setCountries();

        if (savedInstanceState != null) {
            //restore list position
            mLastVisibleIndex = savedInstanceState.getInt(FIRST_VISIBLE_POSITION);
            mOffsetTop = savedInstanceState.getInt(FIRST_VISIBLE_POSITION_TOP);
        }
        return view;
    }

    private void setCountries() {
        if (mCountries != null && getActivity() != null) {
            mListView.setAdapter(new CountryAdapter(getActivity(), mCountries));
            mListView.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.GONE);

            if (mLastVisibleIndex != 0) {
                mListView.setSelectionFromTop(mLastVisibleIndex, mOffsetTop);
                mLastVisibleIndex = 0;
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //here we get first visible item in ListView
        outState.putInt(FIRST_VISIBLE_POSITION, mListView.getFirstVisiblePosition());

        //here we get View of Top view. this view can be visible not full
        View v = mListView.getChildAt(0);
        //get top offset
        outState.putInt(FIRST_VISIBLE_POSITION_TOP, (v == null) ? 0 : v.getTop());
    }

    public void setListCountry(Countries countries) {
        mCountries = countries;
        setCountries();
    }
}