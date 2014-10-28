package by.dzmitryslutskiy.hw.data;

import android.content.Context;
import android.os.Handler;
import android.support.v4.content.AsyncTaskLoader;

import by.dzmitryslutskiy.hw.processing.Processor;

/**
 * DataManager
 * Version information
 * 17.10.2014
 * Created by Dzmitry Slutskiy.
 */
public class DataManager {

    public static interface Callback<Result> {
        void onDataLoadStart();

        void onDone(Result data);

        void onError(Exception e);
    }

    public static <ProcessingResult, DataSourceResult, Params> void
    loadData(
            final Callback<ProcessingResult> callback,
            final Params params,
            final DataSource<DataSourceResult, Params> dataSource,
            final Processor<ProcessingResult, DataSourceResult> processor) {

        if (callback == null) {
            throw new IllegalArgumentException("callback can't be null");
        }
        final Handler handler = new Handler();
        callback.onDataLoadStart();

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    final DataSourceResult result = dataSource.getResult(params);
                    final ProcessingResult processingResult = processor.process(result);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onDone(processingResult);
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

    public static <ProcessingResult, DataSourceResult, Params>
    void loadDataInLoader(Context context,
                          final Callback<ProcessingResult> callback,
                          final Params params,
                          final DataSource<DataSourceResult, Params> dataSource,
                          final Processor<ProcessingResult, DataSourceResult> processor) {
        if (callback == null) {
            throw new IllegalArgumentException("callback can't be null");
        }

        PrivateLoader<ProcessingResult, DataSourceResult, Params> loader =
                new PrivateLoader<ProcessingResult, DataSourceResult, Params>(context,
                        callback, params, dataSource, processor);
        callback.onDataLoadStart();
        loader.forceLoad();
    }

    private static class PrivateLoader<ProcessingResult, DataSourceResult, Params>
            extends AsyncTaskLoader<ProcessingResult> {
        DataSource<DataSourceResult, Params> mDataSource;
        Params mParams;
        Processor<ProcessingResult, DataSourceResult> mProcessor;
        Callback<ProcessingResult> mCallback;
        final Handler mHandler;

        PrivateLoader(Context context,
                      final Callback<ProcessingResult> callback,
                      final Params params,
                      final DataSource<DataSourceResult, Params> dataSource,
                      final Processor<ProcessingResult, DataSourceResult> processor) {
            super(context);
            mDataSource = dataSource;
            mParams = params;
            mProcessor = processor;
            mHandler = new Handler();
            mCallback = callback;
        }


        @Override
        public ProcessingResult loadInBackground() {
            try {
                final DataSourceResult result = mDataSource.getResult(mParams);
                return mProcessor.process(result);
            } catch (final Exception e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mCallback.onError(e);
                    }
                });
            }
            return null;
        }

        @Override
        public void deliverResult(final ProcessingResult data) {
            super.deliverResult(data);
            if (data != null) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mCallback.onDone(data);
                    }
                });
            }
        }
    }


}