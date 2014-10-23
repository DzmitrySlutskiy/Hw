package by.dzmitryslutskiy.hw.ui.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collection;

import by.dzmitryslutskiy.hw.R;
import by.dzmitryslutskiy.hw.bo.NoteGsonModel;

/**
 * Classname
 * Version information
 * 22.10.2014
 * Created by Dzmitry Slutskiy.
 */
public class GsonDetailsActivity extends ActionBarActivity {

    public static final String FULL = "full";
    private String mJsonString;
    private Collection<NoteGsonModel> mCollection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gson_details);

        mJsonString = getIntent().getStringExtra(FULL);

        ((TextView) findViewById(R.id.json_source_string)).setText(
                "get string from main activity:" + mJsonString);
    }

    public void onJsonToNote(View view) {
        Gson gson = new Gson();
        Type collectionType = new TypeToken<Collection<NoteGsonModel>>() {}.getType();
        mCollection = gson.fromJson(mJsonString, collectionType);
        ((TextView) findViewById(R.id.obj_string)).setText(
                "gson.fromJson:" + mCollection
        );
    }

    public void onNoteToJson(View view) {
        Gson gson = new Gson();
        String json = gson.toJson(mCollection);
        ((TextView) findViewById(R.id.json_final_string)).setText(
                "gson.toJson(collection):"+json);
    }
}
