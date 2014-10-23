package by.dzmitryslutskiy.hw.ui.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

import by.dzmitryslutskiy.hw.R;
import by.dzmitryslutskiy.hw.bo.Note;

/**
 * Classname
 * Version information
 * 22.10.2014
 * Created by Dzmitry Slutskiy.
 */
public class DetailsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Note note = getIntent().getParcelableExtra("item");
        //NoteGsonModel noteGsonModel = (NoteGsonModel) getIntent().getSerializableExtra("item");
        ((TextView) findViewById(android.R.id.text1)).setText(note.getTitle());
        ((TextView) findViewById(android.R.id.text2)).setText(note.getContent());
    }

}
