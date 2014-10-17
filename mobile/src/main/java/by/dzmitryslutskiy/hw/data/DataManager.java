package by.dzmitryslutskiy.hw.data;

import android.os.Handler;

import java.util.List;

/**
 * DataManager
 * Version information
 * 17.10.2014
 * Created by Dzmitry Slutskiy.
 */
public class DataManager {

    public static interface Callback {
        void onDataLoadStart();
        void onDone(List<TypeA> data);
        void onError(Exception e);
    }

    public static void loadData(final Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("callback can't be null");
        }
        final Handler handler = new Handler();
        callback.onDataLoadStart();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final List<TypeA> data = DataSource.getData();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onDone(data);
                        }
                    });
                } catch (final Exception e) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onError(e);
                        }
                    });
                }
            }
        }).start();
    }

}