package by.dzmitryslutskiy.hw.ui.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import by.dzmitryslutskiy.hw.R;
import by.dzmitryslutskiy.hw.data.DataManager;
import by.dzmitryslutskiy.hw.data.TypeA;
import by.dzmitryslutskiy.hw.data.TypeFactory;
import by.dzmitryslutskiy.hw.ui.adapters.SampleAdapter;


public class MainActivity extends ActionBarActivity implements DataManager.Callback {

    private TextView textViewEmpty;
    private TextView textViewError;
    private ProgressBar progressBar;
    private ListView mListView;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewEmpty = (TextView) findViewById(android.R.id.empty);
        textViewError = (TextView) findViewById(R.id.error);
        progressBar = (ProgressBar) findViewById(android.R.id.progress);
        mListView = (ListView) findViewById(android.R.id.list);
        mButton = (Button) findViewById(R.id.button_add);
        DataManager.loadData(this);
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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDataLoadStart() {
        progressBar.setVisibility(View.VISIBLE);
        textViewEmpty.setVisibility(View.GONE);
        mButton.setEnabled(false);
    }

    @Override
    public void onDone(List<TypeA> data) {
        progressBar.setVisibility(View.GONE);
        if (data == null || data.isEmpty()) {
            textViewEmpty.setVisibility(View.VISIBLE);
        }
        mButton.setEnabled(true);

        SampleAdapter adapter = new SampleAdapter(this, data);
        mListView.setAdapter(adapter);
    }

    @Override
    public void onError(Exception e) {
        progressBar.setVisibility(View.GONE);
        textViewEmpty.setVisibility(View.GONE);
        mButton.setEnabled(false);

        textViewError.setVisibility(View.VISIBLE);
        textViewError.setText(getString(R.string.error) + "\n" + e.getMessage());
    }

    private static int manualAdded = 0;

    public void onAddItem(View view) {
        TypeA newItem = TypeFactory.getNewItem(
                R.drawable.ic_launcher,
                "", "manual added [" + (++ manualAdded) + "]", R.drawable.ic_launcher);
        SampleAdapter adapter = (SampleAdapter) mListView.getAdapter();
        if (adapter != null) {
            adapter.addItem(newItem);
        }
    }
}
