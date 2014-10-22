package by.dzmitryslutskiy.hw.data;

import android.content.Context;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import by.dzmitryslutskiy.hw.CoreApplication;

/**
 * Classname
 * Version information
 * 22.10.2014
 * Created by Dzmitry Slutskiy.
 */
public class FileDataSource implements DataSource<InputStream, String> {

    public static final String KEY = FileDataSource.class.getSimpleName();

    private Context mContext;
    private FileType mType;

    public enum FileType {
        /**
         * RAW resource
         */
        RAW,
        /**
         * ASSET file
         */
        ASSET,
        /**
         * any file in disk
         */
        EXTERNAL,
        /**
         * check this file name for all types - default for get method
         */
        ANY
    }

    public static FileDataSource get(Context context, FileType type) {
        FileDataSource source = CoreApplication.get(context, KEY);
        source.mContext = context;
        source.mType = type;
        return source;
    }

    public static FileDataSource get(Context context) {
        return get(context, FileType.ANY);
    }

    @Override
    public InputStream getResult(String path) throws Exception {
        switch (mType) {
            case RAW:
                return getResult(Integer.parseInt(path));
            case ASSET:
                return getAssetFileStream(path);
            case EXTERNAL:
                return getExternalFileStream(path);
            default:
            case ANY:
                return getAnyFileStream(path);
        }
    }

    public InputStream getResult(int id) throws Exception {
        return getRawFileStream(id);
    }

    private InputStream getAnyFileStream(String path) throws IOException {
        try {
            return getRawFileStream(Integer.parseInt(path));
        } catch (IOException raw) {
            try {
                return getAssetFileStream(path);
            } catch (IOException asset) {
                return getExternalFileStream(path);
            }
        }
    }

    private InputStream getExternalFileStream(String path) throws IOException {
        return new FileInputStream(path);
    }

    private InputStream getAssetFileStream(String path) throws IOException {
        return mContext.getAssets().open(path);
    }

    private InputStream getRawFileStream(int id) throws IOException {
        return mContext.getResources().openRawResource(id);
    }

}
