package by.dzmitryslutskiy.hw.asyncwork;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.CursorLoader;

import by.dzmitryslutskiy.hw.providers.Contracts.NoteContract;

/**
 * NoteLoader
 * Version information
 * 28.10.2014
 * Created by Dzmitry Slutskiy.
 */
public class NoteDetailLoader extends CursorLoader {
    public static final String ARG_ID = "argId";

    public NoteDetailLoader(Context context, Bundle args) {
        super(context,
//                NoteContract.CONTENT_URI,
                Uri.withAppendedPath(NoteContract.CONTENT_URI, Integer.toString(args.getInt(ARG_ID))),
                NoteContract.defaultColumns,
                null, null, null);
    }
}
