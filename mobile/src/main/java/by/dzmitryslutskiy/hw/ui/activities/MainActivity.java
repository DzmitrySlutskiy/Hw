package by.dzmitryslutskiy.hw.ui.activities;

import android.annotation.TargetApi;
import android.content.Intent;
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
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import by.dzmitryslutskiy.hw.R;
import by.dzmitryslutskiy.hw.asyncwork.ListNoteLoader;
import by.dzmitryslutskiy.hw.asyncwork.TestTask;
import by.dzmitryslutskiy.hw.bo.Note;
import by.dzmitryslutskiy.hw.data.DataManager;
import by.dzmitryslutskiy.hw.data.HttpDataSource;
import by.dzmitryslutskiy.hw.data.HttpRequestParam;
import by.dzmitryslutskiy.hw.processing.NoteArrayProcessor;


public class MainActivity extends ActionBarActivity implements DataManager.Callback<List<Note>> {

    public static final String URL = "https://dl.dropboxusercontent.com/u/16403954/test.json";
    private static final String LOG_TAG = "MainActivity";

    private ArrayAdapter mAdapter;

    private StringLoaderCallback mStringCallback;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private AdapterView listView;
    private View progress;
    private TextView errorView;
    ListView async_test;
    List<String> list;
    private View empty;
    private List<Note> mData;
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
        async_test = (ListView) findViewById(R.id.async_test);

        list= new ArrayList<String>();
        adapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        async_test.setAdapter(adapter);

        final HttpDataSource dataSource = HttpDataSource.get(MainActivity.this);
        final NoteArrayProcessor processor = new NoteArrayProcessor();

        final HttpRequestParam param = HttpRequestParam.newInstance(HttpRequestParam.HttpType.GET, URL);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                DataManager.loadData(MainActivity.this,
                        param,
                        dataSource,
                        processor);
            }
        });

        initLoader();
    }

    private void initLoader() {
        Bundle bundle = new Bundle();
        bundle.putString(ListNoteLoader.PARAM_URL, URL);
        getSupportLoaderManager().initLoader(((Object) this).hashCode(), bundle, getStringCallback());
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
    public void onDone(List<Note> data) {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        progress.setVisibility(View.GONE);
        if (data == null || data.isEmpty()) {
            empty.setVisibility(View.VISIBLE);
        }

        if (mAdapter == null) {
            mData = data;
            mAdapter = new ArrayAdapter<Note>(this, R.layout.adapter_item, android.R.id.text1, data) {

                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    if (convertView == null) {
                        convertView = View.inflate(MainActivity.this, R.layout.adapter_item, null);
                    }
                    Note item = getItem(position);
                    TextView textView1 = (TextView) convertView.findViewById(android.R.id.text1);
                    textView1.setText(item.getTitle());
                    TextView textView2 = (TextView) convertView.findViewById(android.R.id.text2);
                    textView2.setText(item.getContent());
                    convertView.setTag(item.getId());
                    return convertView;
                }

            };
            listView.setAdapter(mAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                    Note item = (Note) mAdapter.getItem(position);
                    /*NoteGsonModel note = new NoteGsonModel(item.getId(), item.getTitle(), item.getContent());
                    intent.putExtra("item", note);*/
                    intent.putExtra("item", item);
                    startActivity(intent);
                }
            });
        } else {
            mData.clear();
            mData.addAll(data);
            mAdapter.notifyDataSetChanged();
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
                Log.d(LOG_TAG, "do AsyncTask()");
                TestTask.init();
                for (int i = 0; i < 5; i++) {
                    TestTask task = new TestTask(mCall);
                    task.execute("" + i);
                }
                return true;

            case R.id.action_restart_loader:
                Bundle bundle = new Bundle();
                bundle.putString(ListNoteLoader.PARAM_URL, URL);
                Log.d(LOG_TAG, "do restartLoader");
                getSupportLoaderManager().restartLoader(((Object) this).hashCode(), bundle, getStringCallback());
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

    private StringLoaderCallback getStringCallback() {
        if (mStringCallback == null) {
            mStringCallback = new StringLoaderCallback();
        }
        return mStringCallback;
    }

    private class StringLoaderCallback implements LoaderManager.LoaderCallbacks<List<Note>> {

        @Override
        public Loader<List<Note>> onCreateLoader(int i, Bundle bundle) {
            Log.d(LOG_TAG, "onCreateLoader:" + i);
            return new ListNoteLoader(getApplicationContext(),
                    bundle.getString(ListNoteLoader.PARAM_URL));
        }

        @Override
        public void onLoadFinished(Loader<List<Note>> loader, List<Note> s) {
            Log.d(LOG_TAG, "onLoadFinished:" + s);
            onDone(s);
        }

        @Override
        public void onLoaderReset(Loader<List<Note>> loader) {
            Log.d(LOG_TAG, "onLoaderReset");
        }
    }

}
