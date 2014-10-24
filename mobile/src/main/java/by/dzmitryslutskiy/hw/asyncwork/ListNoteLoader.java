package by.dzmitryslutskiy.hw.asyncwork;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.List;

import by.dzmitryslutskiy.hw.bo.Note;
import by.dzmitryslutskiy.hw.data.HttpDataSource;
import by.dzmitryslutskiy.hw.data.HttpRequestParam;
import by.dzmitryslutskiy.hw.processing.NoteArrayProcessor;

/**
 * StringLoader
 * Version 1.0
 * 24.10.2014
 * Created by Dzmitry Slutskiy.
 */
public class ListNoteLoader extends AsyncTaskLoader<List<Note>> {
    private static final String LOG_TAG = ListNoteLoader.class.getSimpleName();
    public static final String PARAM_URL = "ListNoteLoader_param_URL";

    private final String mUrl;
    private List<Note> mCache;

    public ListNoteLoader(Context context, String url) {
        super(context);
        mUrl = url;
        Log.d(LOG_TAG, "ListNoteLoader constructor id: " + this);
    }


    @Override
    public List<Note> loadInBackground() {
        Log.d(LOG_TAG, "loadInBackground: " + this);
        final HttpRequestParam param = HttpRequestParam.newInstance(
                HttpRequestParam.HttpType.GET, mUrl);
        HttpDataSource dataSource = HttpDataSource.get(getContext());
        NoteArrayProcessor processor = new NoteArrayProcessor();
        try {
            mCache = processor.process(dataSource.getResult(param));
        } catch (Exception e) {
            e.printStackTrace();
            mCache = null;
        }
        return mCache;
    }

    @Override
    public void deliverResult(List<Note> data) {
        Log.d(LOG_TAG, "deliverResult: " + this);
        super.deliverResult(data);
    }

    @Override
    protected void onStartLoading() {
        Log.d(LOG_TAG, "onStartLoading: " + this);
        if (mCache != null) {
            deliverResult(mCache);
        } else {
            forceLoad();
        }
    }

    @Override
    protected void onReset() {
        Log.d(LOG_TAG, "onReset: " + this);
        super.onReset();
    }

    @Override
    protected void onAbandon() {
        Log.d(LOG_TAG, "onAbandon: " + this);
        super.onAbandon();
    }

    @Override
    protected void onStopLoading() {
        Log.d(LOG_TAG, "onStopLoading: " + this);
        super.onStopLoading();
    }

    @Override
    protected List<Note> onLoadInBackground() {
        Log.d(LOG_TAG, "onLoadInBackground: " + this);
        return super.onLoadInBackground();
    }

    @Override
    protected void onForceLoad() {
        Log.d(LOG_TAG, "onForceLoad: " + this);
        super.onForceLoad();
    }

    @Override
    public void onCanceled(List<Note> data) {
        Log.d(LOG_TAG, "onCanceled: " + this);
        super.onCanceled(data);
    }

    @Override
    protected void finalize() throws Throwable {
        Log.d(LOG_TAG, "finalize: " + this);
        super.finalize();
    }
}
