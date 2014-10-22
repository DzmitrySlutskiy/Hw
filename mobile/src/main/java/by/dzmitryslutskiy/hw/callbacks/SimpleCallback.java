package by.dzmitryslutskiy.hw.callbacks;

import android.util.Log;

import by.dzmitryslutskiy.hw.data.DataManager;

/**
 * SimpleCallback
 * Version information
 * 21.10.2014
 * Created by Dzmitry Slutskiy.
 */

public abstract class SimpleCallback<Result> implements DataManager.Callback {

    @Override
    public void onDataLoadStart() {
        Log.d("SimpleCallback", "onDataLoadStart");
    }

    @Override
    public void onError(Exception e) {
        Log.e("SimpleCallback", "onError", e);
    }

}