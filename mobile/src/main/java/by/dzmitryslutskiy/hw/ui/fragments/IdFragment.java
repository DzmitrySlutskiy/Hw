package by.dzmitryslutskiy.hw.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import by.dzmitryslutskiy.hw.R;

/**
 * IdFragment
 * Version information
 * 10.11.2014
 * Created by Dzmitry Slutskiy.
 */
public class IdFragment extends Fragment implements TextView.OnClickListener {

    public static final String TAG = DetailFragment.class.getSimpleName();

    public static final String ARG_NOTE_ID = "argNoteId";
    public static final String KEY_TRY_SAVE = "tyrSaveString";
    private onIdClickListener mListener;
    private int mNoteId;

    //  UI
    private TextView mTextViewContent;

    public IdFragment() {/*   code    */}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            Log.d("IdFragment", "onCreateView saved string: " + savedInstanceState.getString(KEY_TRY_SAVE));

            mNoteId = savedInstanceState.getInt(ARG_NOTE_ID, Integer.MIN_VALUE);
        } else {
            mNoteId = getNoteIdFromArgs();
        }
        View view = inflater.inflate(R.layout.fragment_id, container, false);
        mTextViewContent = (TextView) view.findViewById(R.id.text_id);
        mTextViewContent.setText("ID: no");
        mTextViewContent.setOnClickListener(this);
        return view;
    }

    public void updateNoteId(int newId) {
        mNoteId = newId;
        mTextViewContent.setText(mNoteId > 0 ? "ID: " + mNoteId : "ID: no");
    }

    private int getNoteIdFromArgs() {
        return getArguments() == null ? - 1 : getArguments().getInt(ARG_NOTE_ID);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("IdFragment", "onSaveInstanceState");
        outState.putString(KEY_TRY_SAVE, "Saved string.");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof onIdClickListener) {
            mListener = (onIdClickListener) activity;
        }
    }

    /**
     * Можно назначить любой объект в качестве listener-а события клика
     *
     * @param listener listener to set
     */
    public void setOnIdClickListener(onIdClickListener listener) {
        mListener = listener;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mListener = null;
    }

    @Override
    public void onClick(View v) {
        if (mListener != null) {
            mListener.onIdClick(mNoteId);
        }
    }

    public interface onIdClickListener {
        void onIdClick(int noteId);
    }
}
