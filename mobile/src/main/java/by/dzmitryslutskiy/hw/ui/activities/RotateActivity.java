package by.dzmitryslutskiy.hw.ui.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import by.dzmitryslutskiy.hw.R;
import by.dzmitryslutskiy.hw.ui.fragments.DetailFragment;
import by.dzmitryslutskiy.hw.ui.fragments.IdFragment;
import by.dzmitryslutskiy.hw.ui.fragments.MainFragment;
import by.dzmitryslutskiy.hw.utils.FragmentUtils;

/**
 * RotateActivity
 * Version information
 * 10.11.2014
 * Created by Dzmitry Slutskiy.
 */
public class RotateActivity extends ActionBarActivity implements MainFragment.onNoteSelectedListener,
        IdFragment.onIdClickListener {

    public static final String SELECTED_NOTE_ID = "mSelectedNoteId";
    private int mSelectedNoteId;

    public RotateActivity() {/*   code    */}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotate);

        FragmentManager manager = getSupportFragmentManager();

        if (savedInstanceState != null) {
            mSelectedNoteId = savedInstanceState.getInt(SELECTED_NOTE_ID);
        }

        if (findViewById(R.id.fragment_detail) != null) {
            //here dual pane mode init detailFragment

            MainFragment mainFragment = (MainFragment) manager.findFragmentById(R.id.fragment_main);
            //if first run initialize fragment and set in position R.id.fragment_main
            if (mainFragment == null) {
                mainFragment = new MainFragment();

                //replace or add fragment
                addFragmentToManager(R.id.fragment_main, mainFragment, MainFragment.TAG);
            }

            DetailFragment detailFragment = (DetailFragment) manager.findFragmentById(R.id.fragment_detail);
            if (detailFragment == null) {
                detailFragment = initDetailFragment(savedInstanceState != null
                        ? savedInstanceState.getInt(SELECTED_NOTE_ID)
                        : - 1);
                //replace or add fragment
                addFragmentToManager(R.id.fragment_detail, detailFragment, DetailFragment.TAG);
            } else {
                detailFragment.updateNoteId(mSelectedNoteId);
            }
        } else {
            MainFragment mainFragment = (MainFragment) FragmentUtils.
                    findFragmentByIdAndTag(getSupportFragmentManager(),
                            R.id.fragment_container, MainFragment.TAG);
            //if first run initialize fragment and set in position R.id.fragment_main
            if (mainFragment == null) {
                mainFragment = new MainFragment();

                //replace or add fragment
                addFragmentToManager(R.id.fragment_container, mainFragment, MainFragment.TAG);
            }
        }
        logFragmentsSize();
    }


    private void logFragmentsSize() {
        List fragmentsList = getSupportFragmentManager().getFragments();
        if (fragmentsList != null) {
            Log.d("RotateActivity", "fragments: " + fragmentsList.size());
        } else {
            Log.d("RotateActivity", "fragments: null");
        }
    }

    private void addFragmentToManager(int id, Fragment fragment, String tag) {
        addFragmentToManager(id, fragment, tag, false);
    }

    private void addFragmentToManager(int id, Fragment fragment, String tag, boolean addToBackStack) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        transaction.add(id, fragment, tag);

        if (addToBackStack) {
            transaction.addToBackStack(tag);
        }
        transaction.commit();
        logFragmentsSize();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        logFragmentsSize();
        outState.putInt(SELECTED_NOTE_ID, mSelectedNoteId);
    }

    private DetailFragment initDetailFragment(int noteId) {
        DetailFragment fragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(DetailFragment.ARG_NOTE_ID, noteId);
        fragment.setArguments(bundle);

        logFragmentsSize();
        return fragment;
    }

    @Override
    public void onNoteSelectedClick(int noteId) {
        mSelectedNoteId = noteId;
        FragmentManager manager = getSupportFragmentManager();

        if (findViewById(R.id.fragment_detail) != null) {
            //here dual pane mode

            DetailFragment detailFragment = (DetailFragment) manager.findFragmentById(R.id.fragment_detail);
            if (detailFragment == null) {
                detailFragment = initDetailFragment(noteId);

                //add fragment
                addFragmentToManager(R.id.fragment_detail, detailFragment, DetailFragment.TAG);
            } else {
                detailFragment.updateNoteId(noteId);
            }
        } else {
            //here single pane mode
            MainFragment mainFragment = (MainFragment) FragmentUtils.
                    findFragmentByIdAndTag(getSupportFragmentManager(),
                            R.id.fragment_container, MainFragment.TAG);

            DetailFragment detailFragment = (DetailFragment) FragmentUtils.
                    findFragmentByIdAndTag(getSupportFragmentManager(),
                            R.id.fragment_container, DetailFragment.TAG);

            if (detailFragment == null) {
                detailFragment = initDetailFragment(noteId);

                //add fragment
                addFragmentToManager(R.id.fragment_container, detailFragment, DetailFragment.TAG);
            } else {
                detailFragment.updateNoteId(noteId);
            }

            FragmentTransaction transaction = manager.beginTransaction();
            if (mainFragment != null) {
                transaction.hide(mainFragment);
            }
            transaction.show(detailFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        logFragmentsSize();
    }

    @Override
    public void onIdClick(int noteId) {
        Toast.makeText(this, "RotateActivity.onIdClick from childFragment: " + noteId, Toast.LENGTH_SHORT).show();
    }
}
