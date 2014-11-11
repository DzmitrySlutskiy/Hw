package by.dzmitryslutskiy.hw.ui.activities;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import by.dzmitryslutskiy.hw.R;
import by.dzmitryslutskiy.hw.asyncwork.ListNoteLoader;
import by.dzmitryslutskiy.hw.asyncwork.NoteLoader;
import by.dzmitryslutskiy.hw.asyncwork.TestTask;
import by.dzmitryslutskiy.hw.bo.Note;
import by.dzmitryslutskiy.hw.data.DataManager;
import by.dzmitryslutskiy.hw.data.HttpDataSource;
import by.dzmitryslutskiy.hw.data.HttpRequestParam;
import by.dzmitryslutskiy.hw.processing.StringProcessor;
import by.dzmitryslutskiy.hw.providers.Contracts.NoteContract;
import by.dzmitryslutskiy.hw.ui.adapters.NoteAdapter;
import by.dzmitryslutskiy.hw.utils.AuthUtils;


public class MainActivity extends ActionBarActivity implements DataManager.Callback<Cursor> {

    public static final String URL = "https://dl.dropboxusercontent.com/u/16403954/test.json";
    private static final String LOG_TAG = "MainActivity";

    public static final int REQUEST_CODE_VK_WEB_VIEW = 0;
    public static final int REQUEST_CODE_VK_NATIVE = 1;

    private NoteAdapter mAdapter;

    private CursorLoaderCallback mCursorCallback;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private AdapterView listView;
    private View progress;
    private TextView errorView;
    List<String> list;
    private View empty;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle pSavedInstanceState) {
        super.onCreate(pSavedInstanceState);
        setContentView(R.layout.activity_main);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        progress = findViewById(android.R.id.progress);
        empty = findViewById(android.R.id.empty);
        listView = (AbsListView) findViewById(android.R.id.list);
        errorView = (TextView) findViewById(R.id.error);

        final HttpDataSource dataSource = HttpDataSource.get(MainActivity.this);
//        final NoteArrayProcessor processor = new NoteArrayProcessor();

        final HttpRequestParam param = HttpRequestParam.newInstance(HttpRequestParam.HttpType.GET, URL);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                DataManager.loadData(MainActivity.this,
//                        param,
//                        dataSource,
//                        processor);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        DataManager.loadDataInLoader(MainActivity.this, new SelfCallback(),
                param,
                dataSource,
                new StringProcessor());

        initLoader();
    }

    public void onWebViewLoginClick(View view) {
        Intent intent = new Intent(this, VkWebViewLoginActivity.class);
        startActivityForResult(intent, REQUEST_CODE_VK_WEB_VIEW);
    }


    public void onBrowserLoginClick(View view) {

        Intent intent = new Intent(this, VkNativeAppAuth.class);
        startActivityForResult(intent, REQUEST_CODE_VK_NATIVE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Log.d(LOG_TAG, "data: " + data);
            if (data != null) {
                Bundle bundle = data.getExtras();

                String result = "GetFrom: " + (requestCode == REQUEST_CODE_VK_WEB_VIEW ? "WebView " :
                        requestCode == REQUEST_CODE_VK_NATIVE ? "Native VK app " : " unknown ") +
                        "user id: " + bundle.get(AuthUtils.USER_ID) +
                        " exp: " + bundle.get(AuthUtils.EXPIRES_IN) +
                        " token: " + bundle.get(AuthUtils.ACCESS_TOKEN);

//                AuthUtils.account(this, bundle.get(AuthUtils.USER_ID).toString(),
//                        bundle.get(AuthUtils.ACCESS_TOKEN).toString());
//                Toast.makeText(this, result, Toast.LENGTH_SHORT).show();

                Log.d(LOG_TAG, result);
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(LOG_TAG, "new intent: " + intent);
    }

    public void onAccountManagerLoginClick(View view) {
        Intent intent = new Intent(this, GoogleAuth.class);
        startActivity(intent);
    }

    public void onOkLoginClick(View view) {
        Intent intent = new Intent(this, OkLogin.class);
        startActivity(intent);
    }

    public void onVkLoginClick(View view) {
        Intent intent = new Intent(this, VkLogin.class);
        startActivity(intent);
    }

    public void onRotateActivityClick(View view) {
        startActivity(new Intent(this, RotateActivity.class));
    }

    public void onStackActivityClick(View view) {
        startActivity(new Intent(this, StackActivity.class));
    }

    public void onPagerActivityClick(View view) {
        startActivity(new Intent(this, PagerActivity.class));
    }

    private class SelfCallback implements DataManager.Callback<String> {

        @Override
        public void onDataLoadStart() {
            Log.d(LOG_TAG, "onDataLoadStart");
        }

        @Override
        public void onDone(String data) {
            Log.d(LOG_TAG, "onDone: " + data);
//            Toast.makeText(getApplicationContext(), "onDone loader in DataManager. data:" + data,
//                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(Exception e) {
            Log.d(LOG_TAG, "onError: " + e);
            Toast.makeText(getApplicationContext(), "onError loader in DataManager. e:" + e,
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void initLoader() {
        Bundle bundle = new Bundle();
        bundle.putString(ListNoteLoader.PARAM_URL, URL);
        getSupportLoaderManager().initLoader(((Object) this).hashCode(), bundle, getCursorCallback());
    }

    @Override
    public void onDataLoadStart() {
        if (! mSwipeRefreshLayout.isRefreshing()) {
            progress.setVisibility(View.VISIBLE);
        }
        empty.setVisibility(View.GONE);
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onDone(Cursor data) {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        progress.setVisibility(View.GONE);
        if (data == null) {
            empty.setVisibility(View.VISIBLE);
        }

        if (mAdapter == null) {
            mAdapter = new NoteAdapter(this, data);
            listView.setAdapter(mAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                    Note item = (Note) mAdapter.getItem(position);

                    intent.putExtra("item", item);
                    startActivity(intent);
                }
            });
            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                    getContentResolver().delete(
                            Uri.withAppendedPath(NoteContract.CONTENT_URI, "" + id),
                            null, null);
                    return true;
                }
            });
        } else {
            mAdapter.swapCursor(data);
        }
    }

    @Override
    public void onError(Exception e) {
        e.printStackTrace();
        progress.setVisibility(View.GONE);
        empty.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
        errorView.setText(errorView.getText() + "\n" + e.getMessage());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case R.id.action_settings:
                Log.d(LOG_TAG, "do System.gc()");
                System.gc();
                return true;

            case R.id.action_task:

                ContentValues values = new ContentValues();
                values.put(NoteContract.COLUMN_TITLE, "Title");
                values.put(NoteContract.COLUMN_CONTENT, "Content");
                getContentResolver().insert(NoteContract.CONTENT_URI, values);
                return true;

            case R.id.action_restart_loader:
                Bundle bundle = new Bundle();
                bundle.putString(ListNoteLoader.PARAM_URL, URL);
                Log.d(LOG_TAG, "do restartLoader");

                getSupportLoaderManager().restartLoader(((Object) this).hashCode(), bundle, getCursorCallback());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private TestCallbackImpl mCall = new TestCallbackImpl();

    private class TestCallbackImpl implements TestTask.TestCallback {

        @Override
        public void onProgress(String... values) {
            list.add(values[0]);
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onFinish(String result) {
            list.add(result);
            adapter.notifyDataSetChanged();
        }
    }

    private CursorLoaderCallback getCursorCallback() {
        if (mCursorCallback == null) {
            mCursorCallback = new CursorLoaderCallback();
        }
        return mCursorCallback;
    }

    private class CursorLoaderCallback implements LoaderManager.LoaderCallbacks<Cursor> {

        @Override
        public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
            Log.d(LOG_TAG, "onCreateLoader:" + i);
            return new NoteLoader(getApplicationContext());
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor s) {
            Log.d(LOG_TAG, "onLoadFinished:" + s);
//            Toast.makeText(getApplicationContext(), "onLoadFinish", Toast.LENGTH_SHORT).show();
            onDone(s);
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
            Log.d(LOG_TAG, "onLoaderReset");
        }
    }

}
