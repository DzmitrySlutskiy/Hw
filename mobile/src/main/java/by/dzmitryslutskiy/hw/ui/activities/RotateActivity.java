package by.dzmitryslutskiy.hw.ui.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

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

    private static final String SELECTED_NOTE_ID = "mSelectedNoteId";
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

            //if first run initialize fragment and set in position R.id.fragment_main
            if (manager.findFragmentById(R.id.fragment_main) == null) {
                addMainFragmentToContainer(R.id.fragment_main);
            }
            updateDetailFragment(mSelectedNoteId,
                    (DetailFragment) manager.findFragmentById(R.id.fragment_detail),
                    R.id.fragment_detail);
        } else {
            //if first run initialize fragment and set in position R.id.fragment_main
            if (FragmentUtils.findFragmentByIdAndTag(manager,
                    R.id.fragment_container, MainFragment.TAG) == null) {
                addMainFragmentToContainer(R.id.fragment_container);
            }
        }
    }

    private void addMainFragmentToContainer(int id) {
        FragmentUtils.addFragment(getSupportFragmentManager(), id,
                new MainFragment(), MainFragment.TAG);
    }

    private void addFragmentToManager(int id, Fragment fragment, String tag) {
        FragmentUtils.addFragment(getSupportFragmentManager(), id, fragment, tag);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(SELECTED_NOTE_ID, mSelectedNoteId);
    }

    private DetailFragment initDetailFragment(int noteId) {
        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(DetailFragment.prepareBundle(noteId));

        return fragment;
    }

    @Override
    public void onNoteSelectedClick(int noteId) {
        mSelectedNoteId = noteId;
        FragmentManager manager = getSupportFragmentManager();

        if (findViewById(R.id.fragment_detail) != null) {
            //here dual pane mode
            updateDetailFragment(noteId,
                    (DetailFragment) manager.findFragmentById(R.id.fragment_detail),
                    R.id.fragment_detail);
        } else {
            //here single pane mode
            MainFragment mainFragment = (MainFragment) FragmentUtils.
                    findFragmentByIdAndTag(manager, R.id.fragment_container, MainFragment.TAG);

            DetailFragment detailFragment = updateDetailFragment(noteId,
                    (DetailFragment) FragmentUtils.findFragmentByIdAndTag(manager,
                            R.id.fragment_container, DetailFragment.TAG), R.id.fragment_container);

            FragmentTransaction transaction = manager.beginTransaction();
            if (mainFragment != null) {
                transaction.hide(mainFragment);
            }
            transaction.show(detailFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    private DetailFragment updateDetailFragment(int noteId, DetailFragment detailFragment, int id) {
        if (detailFragment == null) {
            detailFragment = initDetailFragment(noteId);

            //add fragment
            addFragmentToManager(id, detailFragment, DetailFragment.TAG);
        } else {
            detailFragment.updateNoteId(noteId);
        }
        return detailFragment;
    }

    @Override
    public void onIdClick(int noteId) {
        Toast.makeText(this, "RotateActivity.onIdClick from childFragment: " + noteId, Toast.LENGTH_SHORT).show();
    }
}