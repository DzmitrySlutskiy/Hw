package by.dzmitryslutskiy.hw.providers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import by.dzmitryslutskiy.hw.providers.Contracts.NoteContract;
import by.dzmitryslutskiy.hw.providers.Contracts.UserContract;

/**
 * DBHelper
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
        Log.i(LOG_TAG, "onCreate database");

        NoteContract.onCreate(db);
        UserContract.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(LOG_TAG, "onUpgrade database from ver: " + oldVersion + " to ver: " + newVersion);

        NoteContract.onUpgrade(db, oldVersion, newVersion);
        UserContract.onUpgrade(db, oldVersion, newVersion);
    }

    public Cursor query(String table, String[] columns, String selection,
                        String[] selectionArgs, String groupBy, String having,
                        String orderBy) {
        SQLiteDatabase readable = getReadableDatabase();
        return readable.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
    }

    public long insert(String table, String nullColumnHack, ContentValues values) {
        return update(table, new ContentValues[]{values}, nullColumnHack, null, false);
    }

    public long insert(String table, String nullColumnHack, ContentValues[] values) {
        return update(table, values, nullColumnHack, null, false);
    }

    public long update(String table, ContentValues values, String whereClause, String[] whereArgs) {
        return update(table, new ContentValues[]{values}, whereClause, whereArgs, true);
    }

    public int delete(String table, String whereClause, String[] whereArgs) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        return writableDatabase.delete(table, whereClause, whereArgs);
    }

    /**
     * Run update or insert query. if isUpdate = false whereClause used as nullColumnHack for insert
     * query. Query run in transaction.
     *
     * @param table       table name
     * @param values      values for insert/update
     * @param whereClause whereClause for update or nullColumnHack for insert
     * @param whereArgs   arguments
     * @param isUpdate    if = true used update query else used insert query
     * @return the number of rows affected by updates or number rows inserted (or -1)
     */
    private long update(String table, ContentValues[] values, String whereClause,
                        String[] whereArgs, boolean isUpdate) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        long result = -1;
        writableDatabase.beginTransaction();
        try {
            for (ContentValues value : values) {     //more than one used for bulkInsert
                if (isUpdate) {
                    result = writableDatabase.update(table, value, whereClause, whereArgs);
                } else {
                    result = writableDatabase.insert(table, whereClause, value);
                }
            }
            //-1 indicate on insertion error see SQLiteDatabase.insert source
//            if (result > 0) {
            writableDatabase.setTransactionSuccessful();
//            }
            return result;
        } finally {
            writableDatabase.endTransaction();
        }
    }
}