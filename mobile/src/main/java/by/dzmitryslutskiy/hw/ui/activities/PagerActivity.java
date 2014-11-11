package by.dzmitryslutskiy.hw.ui.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import by.dzmitryslutskiy.hw.R;
import by.dzmitryslutskiy.hw.ui.adapters.SamplePagerAdapter;

/**
 * PagerActivity
 * Version information
 * 10.11.2014
 * Created by Dzmitry Slutskiy.
 */
public class PagerActivity extends ActionBarActivity {
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

        // Specify that tabs should be displayed in the action bar.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create a tab listener that is called when the user changes tabs.
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


        mAdapter = new SamplePagerAdapter(actionBar, mTabListener, getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);

        for (int i = 0; i < SamplePagerAdapter.MIN_COUNT; i++) {
            mAdapter.add("Tab " + (i + 1));
        }
        mAdapter.notifyDataSetChanged();

        mViewPager.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        getSupportActionBar().setSelectedNavigationItem(position);
                    }
                });
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
                System.gc();
                Toast.makeText(this, "Page count: " + mAdapter.getCount(), Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}