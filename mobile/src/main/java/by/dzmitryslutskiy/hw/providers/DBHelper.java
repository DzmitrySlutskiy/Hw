package by.dzmitryslutskiy.hw.providers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import by.dzmitryslutskiy.hw.providers.Contracts.NoteContract;

/**
 * Classname
 * Version information
 * 28.10.2014
 * Created by Dzmitry Slutskiy.
 */
class DBHelper extends SQLiteOpenHelper {

    private static final String LOG_TAG = DBHelper.class.getSimpleName();
    private static final String DEFAULT_DB_NAME = "test.db";
    private static final int DB_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DEFAULT_DB_NAME, null, DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        NoteContract.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        NoteContract.onUpgrade(db, oldVersion, newVersion);
    }
}
