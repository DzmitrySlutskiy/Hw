package by.dzmitryslutskiy.hw.providers.Contracts;

import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import java.util.HashMap;

/**
 * NoteContract
 * Version 1.0
 * 28.10.2014
 * Created by Dzmitry Slutskiy.
 */
public class NoteContract extends BaseContract{

    public static final String PATH = "Note";

    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_CONTENT = "content";

    public static final Uri CONTENT_URI =
            Uri.withAppendedPath(TestProviderContract.AUTHORITY_URI, PATH);

    private static final String DATABASE_CREATE = "create table "
            + PATH
            + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_TITLE + " text, "
            + COLUMN_CONTENT + " text );";

    public static final HashMap<String, String> PROJECTION_MAP = new HashMap<String, String>();

    public static final String[] defaultColumns =
            new String[]{COLUMN_ID, COLUMN_TITLE, COLUMN_CONTENT};

    private static final String[] availableColumns =
            new String[]{COLUMN_ID, COLUMN_TITLE, COLUMN_CONTENT};

    static {
        PROJECTION_MAP.put(COLUMN_ID, PATH + "." + COLUMN_ID);
        PROJECTION_MAP.put(COLUMN_TITLE, PATH + "." + COLUMN_TITLE);
        PROJECTION_MAP.put(COLUMN_CONTENT, PATH + "." + COLUMN_CONTENT);
    }

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion,
                                 int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + PATH);
        onCreate(database);
    }

    private NoteContract() {/*   code    */}

}
