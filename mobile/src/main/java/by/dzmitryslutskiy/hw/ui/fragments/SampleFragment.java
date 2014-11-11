package by.dzmitryslutskiy.hw.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import by.dzmitryslutskiy.hw.R;

/**
 * SampleFragment
 * Version information
 * 11.11.2014
 * Created by Dzmitry Slutskiy.
 */
public class SampleFragment extends Fragment {
    public static int COUNTER = 0;
    public static final Object mCounterLocker = new Object();

    public static final String ARG_TEXT = "argText";

    private String mText;
    //  UI
    private TextView mTextViewContent;

    public SampleFragment() {
        synchronized (mCounterLocker) {
            COUNTER++;
            Log.d("SampleFragment", "count: " + COUNTER);
        }
    }

    /*@Override
    protected void finalize() throws Throwable {
        super.finalize();
        synchronized (mCounterLocker) {
            COUNTER--;
            Log.d("SampleFragment.finalize", "count: " + COUNTER);
        }
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mText = getTextFromArgs();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mText = savedInstanceState.getString(ARG_TEXT);
        } else {
            mText = getTextFromArgs();
        }
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        mTextViewContent = (TextView) view.findViewById(android.R.id.text1);
        mTextViewContent.setText(mText + " this: " + this);

        return view;
    }

    private String getTextFromArgs() {
        return getArguments() == null ? "" : getArguments().getString(ARG_TEXT);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(ARG_TEXT, mText);
    }

    public void updateText(String newText) {
        mText = newText;
        if (isResumed() && mTextViewContent != null) {
            mTextViewContent.setText(mText);
        }
    }

    public static Bundle prepareBundle(String text) {
        Bundle bundle = new Bundle();
        bundle.putString(ARG_TEXT, text);

        return bundle;
    }
}