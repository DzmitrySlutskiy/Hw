package by.dzmitryslutskiy.hw.ui.fragments;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import by.dzmitryslutskiy.hw.R;
import by.dzmitryslutskiy.hw.asyncwork.NoteLoader;
import by.dzmitryslutskiy.hw.providers.Contracts.NoteContract;
import by.dzmitryslutskiy.hw.ui.adapters.NoteBaseAdapter;

/**
 * MainFragment
 * Version information
 * 10.11.2014
 * Created by Dzmitry Slutskiy.
 */
public class MainFragment extends ListFragment {
    public static final String TAG = MainFragment.class.getSimpleName();
    private static final int MAIN_FRAGMENT_LOADER_ID = 0;

    private CursorLoaderCallback mCursorCallback;
    private onNoteSelectedListener mListener;


    public MainFragment() {/*   code    */}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        initLoader();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        if (mListener != null) {
            mListener.onNoteSelectedClick((int) id);
        }
    }

    private class CursorLoaderCallback implements LoaderManager.LoaderCallbacks<Cursor> {

        @Override
        public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
            return new NoteLoader(getActivity());
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor s) {
            setCursorToAdapter(s);
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
            setCursorToAdapter(null);
        }

        private void setCursorToAdapter(Cursor s) {
            CursorAdapter adapter = (CursorAdapter) getListAdapter();
            if (adapter == null) {
                setListAdapter(new NoteBaseAdapter(getActivity(), s, NoteContract.COLUMN_TITLE));
            } else {
                adapter.swapCursor(s);
            }
        }
    }

    private CursorLoaderCallback getCursorCallback() {
        if (mCursorCallback == null) {
            mCursorCallback = new CursorLoaderCallback();
        }
        return mCursorCallback;
    }

    private void initLoader() {
        getLoaderManager().initLoader(MAIN_FRAGMENT_LOADER_ID, null, getCursorCallback());
    }

    public interface onNoteSelectedListener {
        public void onNoteSelectedClick(int id);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof onNoteSelectedListener) {
            mListener = (onNoteSelectedListener) activity;
        }
    }
}
