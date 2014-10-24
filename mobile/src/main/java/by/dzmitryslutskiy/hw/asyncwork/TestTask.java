package by.dzmitryslutskiy.hw.asyncwork;

import android.util.Log;

import java.util.concurrent.TimeUnit;

/**
 * Classname
 * Version information
 * 24.10.2014
 * Created by Dzmitry Slutskiy.
 */
public class TestTask extends MyAsyncTask<String, String, String> {

    private TestCallback mCallback;

    public TestTask(TestCallback callback) {
        mCallback = callback;
    }

    @Override
    protected String doInBackground(String... params) {
        int counter = 5;
        while (counter > 0) {
            counter--;

            publishProgress("TestTask: " + counter + " for param: " + params[0]);

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                return "InterruptedException: " + this;
            }
        }
        return "ready: " + this;
    }


    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        Log.d("onProgressUpdate", values[0]);
        if (mCallback!=null){
            mCallback.onProgress(values);
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.d("onPostExecute", s);
        if (mCallback!=null){
            mCallback.onFinish(s);
        }
    }

    public interface TestCallback{
        void onProgress(String... values);
        void onFinish(String result);
    }
}
