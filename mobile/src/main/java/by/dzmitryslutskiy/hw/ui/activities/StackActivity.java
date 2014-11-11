package by.dzmitryslutskiy.hw.ui.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

import by.dzmitryslutskiy.hw.R;
import by.dzmitryslutskiy.hw.ui.fragments.SampleFragment;

/**
 * RotateActivity
 * Version information
 * 10.11.2014
 * Created by Dzmitry Slutskiy.
 */
public class StackActivity extends ActionBarActivity implements FragmentManager.OnBackStackChangedListener {

    public static final int BACK_TO_POSITION = 9;

    public StackActivity() {/*   code    */}

    private Button mButtonAdd;
    private int mFragmentCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stack);
        mButtonAdd = (Button) findViewById(R.id.button_add);
        onAddFragment(mButtonAdd);

        //for update button caption
        getSupportFragmentManager().addOnBackStackChangedListener(this);
    }

    public void onAddFragment(View view) {
        SampleFragment fragment = new SampleFragment();
        fragment.setArguments(SampleFragment.prepareBundle("This is " + (++ mFragmentCounter) + " fragment"));

        String tag = Integer.toString(mFragmentCounter);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.fragment_container, fragment, tag);
        transaction.addToBackStack(tag);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FragmentManager manager = getSupportFragmentManager();
        if (manager.getBackStackEntryCount() > BACK_TO_POSITION) {
            FragmentManager.BackStackEntry entry = manager.getBackStackEntryAt(BACK_TO_POSITION);
            if (entry != null) {
//                manager.popBackStackImmediate(entry.getName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
                manager.popBackStack(entry.getName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        }
    }


    @Override
    public void onBackStackChanged() {
        mButtonAdd.setText(getString(R.string.add_fragment) + " [" + mFragmentCounter +
                "] back stack count: " + getSupportFragmentManager().getBackStackEntryCount());
    }
}