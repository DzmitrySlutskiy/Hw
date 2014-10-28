package by.dzmitryslutskiy.hw.asyncwork;

import android.content.Context;
import android.support.v4.content.CursorLoader;

import by.dzmitryslutskiy.hw.providers.Contracts.NoteContract;

/**
 * Classname
 * Version information
 * 28.10.2014
 * Created by Dzmitry Slutskiy.
 */
public class NoteLoader extends CursorLoader {
    public NoteLoader(Context context) {
        super(context, NoteContract.CONTENT_URI, NoteContract.defaultColumns, null, null, null);
    }
}
