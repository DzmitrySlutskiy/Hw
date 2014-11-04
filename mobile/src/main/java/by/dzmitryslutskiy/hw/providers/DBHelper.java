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

    /**
     * Convenience method for updating rows in the database.
     *
     * @param table       the table to update in
     * @param values      a map from column names to new column values. null is a
     *                    valid value that will be translated to NULL.
     * @param whereClause the optional WHERE clause to apply when updating.
     *                    Passing null will update all rows.
     * @param whereArgs   You may include ?s in the where clause, which
     *                    will be replaced by the values from whereArgs. The values
     *                    will be bound as Strings.
     * @return the number of rows affected
     */
    public long update(String table, ContentValues values, String whereClause, String[] whereArgs) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        writableDatabase.beginTransaction();
        try {
            long result = writableDatabase.update(table, values, whereClause, whereArgs);
            writableDatabase.setTransactionSuccessful();
            return result;
        } finally {
            writableDatabase.endTransaction();
        }
    }

    /**
     * Convenience method for deleting rows in the database.
     *
     * @param table       the table to delete from
     * @param whereClause the optional WHERE clause to apply when deleting.
     *                    Passing null will delete all rows.
     * @param whereArgs   You may include ?s in the where clause, which
     *                    will be replaced by the values from whereArgs. The values
     *                    will be bound as Strings.
     * @return the number of rows affected if a whereClause is passed in, 0
     * otherwise. To remove all rows and get a count pass "1" as the
     * whereClause.
     */
    public int delete(String table, String whereClause, String[] whereArgs) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        return writableDatabase.delete(table, whereClause, whereArgs);
    }

    /**
     * Convenience method for inserting a row/rows into the database.
     *
     * @param table          the table to insert the row into
     * @param nullColumnHack optional; may be <code>null</code>.
     *                       SQL doesn't allow inserting a completely empty row without
     *                       naming at least one column name.  If your provided <code>values</code> is
     *                       empty, no column names are known and an empty row can't be inserted.
     *                       If not set to null, the <code>nullColumnHack</code> parameter
     *                       provides the name of nullable column name to explicitly insert a NULL into
     *                       in the case where your <code>values</code> is empty.
     * @param values         this map contains the initial column values for the
     *                       row. The keys should be the column names and the values the
     *                       column values
     * @return the row ID of the newly inserted row, or -1 if an error occurred.
     */
    public long insert(String table, String nullColumnHack, ContentValues... values) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        long result = - 1;
        long rowsInserted = 0;
        writableDatabase.beginTransaction();
        try {
            for (ContentValues value : values) {     //more than one used for bulkInsert
                result = writableDatabase.insert(table, nullColumnHack, value);

                if (values.length > 1 && result != - 1) {   //calc count rows inserted
                    rowsInserted++;
                }
            }
            writableDatabase.setTransactionSuccessful();
            return (values.length > 1 ? rowsInserted : result);
        } finally {
            writableDatabase.endTransaction();
        }
    }
}