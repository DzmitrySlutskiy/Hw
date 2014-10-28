package by.dzmitryslutskiy.hw.ui.activities;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;

import by.dzmitryslutskiy.hw.R;
import by.dzmitryslutskiy.hw.bo.Note;
import by.dzmitryslutskiy.hw.providers.Contracts.NoteContract;

/**
 * Classname
 * Version information
 * 22.10.2014
 * Created by Dzmitry Slutskiy.
 */
public class DetailsActivity extends ActionBarActivity {
    Note mNote;
    EditText mTitle;
    EditText mContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        mNote = getIntent().getParcelableExtra("item");

        mTitle = (EditText) findViewById(R.id.edit_text_title);
        mTitle.setText(mNote.getTitle());

        mContent = ((EditText) findViewById(R.id.edit_text_content));
        mContent.setText(mNote.getContent());
    }

    public void onSaveClick(View view) {
        ContentValues values = new ContentValues();
        values.put(NoteContract.COLUMN_TITLE, mTitle.getText().toString());
        values.put(NoteContract.COLUMN_CONTENT, mContent.getText().toString());
        getContentResolver().update(
                Uri.withAppendedPath(NoteContract.CONTENT_URI, "" + mNote.getId()),
                values, null, null);
        finish();
    }

    public void onCancelClick(View view) {
        finish();
    }
}
