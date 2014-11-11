package by.dzmitryslutskiy.hw.asyncwork;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.AsyncTaskLoader;

import by.dzmitryslutskiy.hw.bo.Countries;
import by.dzmitryslutskiy.hw.callbacks.ErrorCallback;
import by.dzmitryslutskiy.hw.data.HttpDataSource;
import by.dzmitryslutskiy.hw.data.HttpRequestParam;

/**
 * Classname
 * Version information
 * 11.11.2014
 * Created by Dzmitry Slutskiy.
 */
public class CountryLoader extends AsyncTaskLoader<Countries> {

    private static final String URL = "https://dl.dropboxusercontent.com/u/30468926/country.json";

    private Countries mCountries;
    private ErrorCallback mCallback;

    public CountryLoader(Context context, ErrorCallback callback) {
        super(context);
        mCallback = callback;
    }

    @Override
    public Countries loadInBackground() {
        final HttpDataSource dataSource = HttpDataSource.get(getContext());
        final HttpRequestParam param = HttpRequestParam.newInstance(HttpRequestParam.HttpType.GET, URL);

        try {
            mCountries = new Countries(dataSource.getResult(param));
        } catch (final Exception e) {
            mCountries = null;
            if (mCallback != null) {
                Handler handler = new Handler(Looper.getMainLooper());

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        mCallback.onError(e);
                    }
                });
            }
        }
        return mCountries;
    }

    @Override
    protected void onStartLoading() {
        if (mCountries != null) {
            deliverResult(mCountries);
        } else {
            forceLoad();
        }
    }
}
