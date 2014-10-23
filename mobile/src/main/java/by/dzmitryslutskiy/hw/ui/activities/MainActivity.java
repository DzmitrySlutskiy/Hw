package by.dzmitryslutskiy.hw.ui.activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import by.dzmitryslutskiy.hw.R;
import by.dzmitryslutskiy.hw.bo.Note;
import by.dzmitryslutskiy.hw.data.DataManager;
import by.dzmitryslutskiy.hw.data.HttpDataSource;
import by.dzmitryslutskiy.hw.data.HttpRequestParam;
import by.dzmitryslutskiy.hw.processing.NoteArrayProcessor;
import by.dzmitryslutskiy.hw.processing.StringProcessor;


public class MainActivity extends ActionBarActivity implements DataManager.Callback<List<Note>> {

    public static final String URL = "https://dl.dropboxusercontent.com/u/16403954/test.json";
    private ArrayAdapter mAdapter;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private String mResultString;
    @Override
    protected void onCreate(Bundle pSavedInstanceState) {
        super.onCreate(pSavedInstanceState);
        setContentView(R.layout.activity_main);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        final HttpDataSource dataSource = HttpDataSource.get(MainActivity.this);
        final NoteArrayProcessor processor = new NoteArrayProcessor();
        final StringProcessor strProcessor = new StringProcessor();

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

        final HttpRequestParam param2 = HttpRequestParam.newInstance(HttpRequestParam.HttpType.GET, URL);
        DataManager.loadData(MainActivity.this,
                param2,
                dataSource,
                processor);

        final HttpRequestParam param3 = HttpRequestParam.newInstance(HttpRequestParam.HttpType.GET, URL);
        DataManager.loadData(new StringOnDone(),
                param3,
                dataSource,
                strProcessor);
    }

    @Override
    public void onDataLoadStart() {
        if (! mSwipeRefreshLayout.isRefreshing()) {
            findViewById(android.R.id.progress).setVisibility(View.VISIBLE);
        }
        findViewById(android.R.id.empty).setVisibility(View.GONE);
    }

    private List<Note> mData;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onDone(List<Note> data) {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        findViewById(android.R.id.progress).setVisibility(View.GONE);
        if (data == null || data.isEmpty()) {
            findViewById(android.R.id.empty).setVisibility(View.VISIBLE);
        }
        AdapterView listView = (AbsListView) findViewById(android.R.id.list);
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


    class StringOnDone implements DataManager.Callback<String>{

        @Override
        public void onDataLoadStart() {

        }

        @Override
        public void onDone(String data) {
            mResultString = data;
        }

        @Override
        public void onError(Exception e) {

        }
    }

    @Override
    public void onError(Exception e) {
        e.printStackTrace();
        findViewById(android.R.id.progress).setVisibility(View.GONE);
        findViewById(android.R.id.empty).setVisibility(View.GONE);
        TextView errorView = (TextView) findViewById(R.id.error);
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
                return true;

            case R.id.action_gson_test:
                Intent intent = new Intent(MainActivity.this, GsonDetailsActivity.class);

                intent.putExtra(GsonDetailsActivity.FULL, mResultString);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
