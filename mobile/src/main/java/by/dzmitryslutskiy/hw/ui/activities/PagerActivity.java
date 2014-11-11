package by.dzmitryslutskiy.hw.ui.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import by.dzmitryslutskiy.hw.R;
import by.dzmitryslutskiy.hw.asyncwork.CountryLoader;
import by.dzmitryslutskiy.hw.bo.Countries;
import by.dzmitryslutskiy.hw.callbacks.ErrorCallback;
import by.dzmitryslutskiy.hw.ui.adapters.SamplePagerAdapter;
import by.dzmitryslutskiy.hw.ui.fragments.CountryFragment;

/**
 * PagerActivity
 * Version information
 * 10.11.2014
 * Created by Dzmitry Slutskiy.
 */
public class PagerActivity extends ActionBarActivity implements LoaderManager.LoaderCallbacks<Countries>, ErrorCallback {
    public static final String TAG = PagerActivity.class.getSimpleName();

    public PagerActivity() {/*   code    */}

    ViewPager mViewPager;
    ActionBar.TabListener mTabListener;
    SamplePagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);

        final ActionBar actionBar = getSupportActionBar();
        mViewPager = (ViewPager) findViewById(R.id.pager);

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);


        mTabListener = new ActionBar.TabListener() {
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                mViewPager.setCurrentItem(tab.getPosition());
                Log.d(TAG, "onTabSelected:" + tab.getPosition());
            }

            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
                Log.d(TAG, "onTabUnselected:" + tab.getPosition());
            }

            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
                Log.d(TAG, "onTabReselected:" + tab.getPosition());
            }
        };

        mAdapter = new SamplePagerAdapter(getSupportActionBar(), mTabListener, getSupportFragmentManager());
        //init data for first run
        if (savedInstanceState == null) {
            for (int i = 0; i < SamplePagerAdapter.MIN_COUNT; i++) {
                mAdapter.add("Tab " + (i + 1));
            }
            mAdapter.notifyDataSetChanged();
        }

        mViewPager.setAdapter(mAdapter);

        mViewPager.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        getSupportActionBar().setSelectedNavigationItem(position);
                    }
                });
        getSupportLoaderManager().initLoader(0, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pager, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.add_tab:
                final ActionBar actionBar = getSupportActionBar();
                mAdapter.add("Tab " + actionBar.getTabCount());
                mAdapter.notifyDataSetChanged();
                return true;

            case R.id.current_size:
                System.gc();        //check Fragments finalize
                Toast.makeText(this, "Page count: " + mAdapter.getCount(), Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public Loader<Countries> onCreateLoader(int i, Bundle bundle) {
        return new CountryLoader(this, this);
    }

    @Override
    public void onLoadFinished(Loader<Countries> countriesLoader, Countries countries) {
        mAdapter.updateCountryList(countries);

        //set data to all fragments, bad idea but not need load same data more than one
        FragmentManager manager = getSupportFragmentManager();
        List<Fragment> list = manager.getFragments();

        if (list != null && list.size() > 0) {
            for (Fragment fragment : list) {
                if (fragment instanceof CountryFragment) {
                    CountryFragment countryFragment = (CountryFragment) fragment;
                    countryFragment.setListCountry(countries);
                }
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Countries> countriesLoader) {
        //do nothing
    }

    @Override
    public void onError(Exception e) {
        Toast.makeText(this, "Get error in load process: " + e, Toast.LENGTH_SHORT).show();
    }
}