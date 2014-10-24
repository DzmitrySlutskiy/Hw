package by.dzmitryslutskiy.hw.asyncwork;

import android.os.Handler;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Classname
 * Version information
 * 24.10.2014
 * Created by Dzmitry Slutskiy.
 */
public abstract class MyAsyncTask<Params, Progress, Result> {

    private static final Executor executor;
    private static final Handler sHandler;
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();

    static {
        executor = Executors.newFixedThreadPool(CPU_COUNT);
        sHandler = new Handler();
    }

    public MyAsyncTask() {
    }

    public static void init() {
        sHandler.getLooper();
    }

    public void execute(final Params... params) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                final Result result = doInBackground(params);

                sHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        onPostExecute(result);
                    }
                });

            }
        });
    }

    protected final void publishProgress(final Progress... values) {
        if (! isCancelled()) {
            sHandler.post(new Runnable() {
                @Override
                public void run() {
                    onProgressUpdate(values);
                }
            });
        }
    }

    protected abstract Result doInBackground(Params... params);

    @SuppressWarnings({"UnusedDeclaration"})
    protected void onPostExecute(Result result) {
    }


    @SuppressWarnings({"UnusedDeclaration"})
    protected void onProgressUpdate(Progress... values) {
    }

    private final AtomicBoolean mCancelled = new AtomicBoolean(false);

    public boolean isCancelled() {
        return mCancelled.get();
    }

    public void cancel() {
        mCancelled.set(true);
    }
}
