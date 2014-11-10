package by.dzmitryslutskiy.hw.ui.fragments;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import by.dzmitryslutskiy.hw.R;
import by.dzmitryslutskiy.hw.asyncwork.NoteDetailLoader;
import by.dzmitryslutskiy.hw.providers.Contracts.NoteContract;

/**
 * DetailFragment
 * Version information
 * 10.11.2014
 * Created by Dzmitry Slutskiy.
 */
public class DetailFragment extends Fragment {
    public static final String TAG = DetailFragment.class.getSimpleName();

    public static final String ARG_NOTE_ID = "argNoteId";

    private int mNoteId;
    private CursorLoaderCallback mCursorCallback;

    //  UI
    private TextView mTextViewContent;

    public DetailFragment() {/*   code    */}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mNoteId = getNoteIdFromArgs();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mNoteId = savedInstanceState.getInt(ARG_NOTE_ID, Integer.MIN_VALUE);
        } else {
            mNoteId = getNoteIdFromArgs();
        }
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        mTextViewContent = (TextView) view.findViewById(android.R.id.text1);

        if (savedInstanceState == null) {
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            IdFragment fragment = new IdFragment();
            transaction.add(R.id.child_fragment, fragment, IdFragment.TAG);
            transaction.commit();
        }

        return view;
    }

    private int getNoteIdFromArgs() {
        return getArguments() == null ? - 1 : getArguments().getInt(ARG_NOTE_ID);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ARG_NOTE_ID, mNoteId);
    }

    @Override
    public void onStart() {
        super.onStart();

        initLoader();
    }

    private class CursorLoaderCallback implements LoaderManager.LoaderCallbacks<Cursor> {

        @Override
        public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
            return new NoteDetailLoader(getActivity(), bundle);
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
            if (cursor != null) {
                cursor.moveToFirst();
                String content;
                if (cursor.getCount() > 0) {
                    content = "Content for selected item: " +
                            cursor.getString(cursor.getColumnIndex(NoteContract.COLUMN_CONTENT));
                } else {
                    content = getActivity().getString(R.string.no_selected_item);
                }
                mTextViewContent.setText(content);

                updateChildFragment();
            }
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
            mTextViewContent.setText(getActivity().getString(R.string.no_selected_item));
        }

    }

    private void updateChildFragment() {
        IdFragment fragment = (IdFragment) getChildFragmentManager().findFragmentByTag(IdFragment.TAG);
        if (fragment != null) {
            fragment.updateNoteId(mNoteId);
        }
    }

    private CursorLoaderCallback getCursorCallback() {
        if (mCursorCallback == null) {
            mCursorCallback = new CursorLoaderCallback();
        }
        return mCursorCallback;
    }

    private void initLoader() {
        Bundle bundle = new Bundle();
        bundle.putInt(NoteDetailLoader.ARG_ID, mNoteId);

        getLoaderManager().initLoader(mNoteId, bundle, getCursorCallback());
    }

    public void updateNoteId(int newId) {
        mNoteId = newId;
        updateChildFragment();
        initLoader();
    }

    public static Bundle prepareBundle(int id) {
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_NOTE_ID, id);
        return bundle;
    }
}
