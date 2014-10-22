package by.dzmitryslutskiy.hw;

import android.app.Application;
import android.content.Context;

import by.dzmitryslutskiy.hw.data.FileDataSource;
import by.dzmitryslutskiy.hw.data.HttpDataSource;

/**
 * Classname
 * Version information
 * 21.10.2014
 * Created by Dzmitry Slutskiy.
 */
public class CoreApplication extends Application {

    private HttpDataSource mHttpDataSource;
    private FileDataSource mFileDataSource;

    @Override
    public void onCreate() {
        super.onCreate();
//        mHttpDataSource = new HttpDataSource(); make lazy init
    }

    @Override
    public Object getSystemService(String name) {
        if (HttpDataSource.KEY.equals(name)) {
            return getHttpDataSource();
        } else if (FileDataSource.KEY.equals(name)) {
            return getFileDataSource();
        }
        return super.getSystemService(name);
    }

    private Object getFileDataSource() {
        if (mFileDataSource == null) {
            mFileDataSource = new FileDataSource();
        }
        return mFileDataSource;
    }

    private Object getHttpDataSource() {
        if (mHttpDataSource == null) {
            mHttpDataSource = new HttpDataSource();
        }
        return mHttpDataSource;
    }

    public static <T> T get(Context context, String key) {
        if (context == null || key == null) {
            throw new IllegalArgumentException("Context and key must not be null");
        }
        T systemService = (T) context.getSystemService(key);
        if (systemService == null) {
            context = context.getApplicationContext();
            systemService = (T) context.getSystemService(key);
        }
        if (systemService == null) {
            throw new IllegalStateException(key + " not available");
        }
        return systemService;
    }
}
