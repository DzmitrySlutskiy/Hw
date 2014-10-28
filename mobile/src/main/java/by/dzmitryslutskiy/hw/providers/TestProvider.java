package by.dzmitryslutskiy.hw.providers;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

import by.dzmitryslutskiy.hw.providers.Contracts.NoteContract;

/**
 * Classname
 * Version information
 * 28.10.2014
 * Created by Dzmitry Slutskiy.
 */
public class TestProvider extends ContentProvider {

    public static final String LOG_TAG = TestProvider.class.getSimpleName();

    private static final int CODE_NOTE = 10;
    private static final int CODE_NOTE_ID = 20;

    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private DBHelper mDBHelper;

    static {
        sURIMatcher.addURI(NoteContract.AUTHORITY, NoteContract.PATH, CODE_NOTE);
        sURIMatcher.addURI(NoteContract.AUTHORITY, NoteContract.PATH + "/#", CODE_NOTE_ID);

    }


    public TestProvider() {/*   code    */}

    @Override
    public boolean onCreate() {
        mDBHelper = new DBHelper(getContext());
        return false;
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        int uriType = sURIMatcher.match(uri);
        switch (uriType) {

            case CODE_NOTE:
                queryBuilder.setTables(NoteContract.PATH);
                break;

            case CODE_NOTE_ID:
                queryBuilder.setTables(NoteContract.PATH);
                queryBuilder.appendWhere(NoteContract.PATH + "." + NoteContract.COLUMN_ID + "="
                        + uri.getLastPathSegment());
                break;

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        Cursor cursor = null;
        try {
            cursor = queryBuilder.query(db, projection, selection,
                    selectionArgs, null, null, sortOrder);

            // make sure that potential listeners are getting notified
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        } catch (Exception e) {
            Log.e(LOG_TAG, "" + e);
        }
        return cursor;

    }


    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri resultUri;
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        long id;

        switch (uriType) {

            case CODE_NOTE_ID:
            case CODE_NOTE:
                id = db.insert(NoteContract.PATH, null, values);
                resultUri = Uri.withAppendedPath(NoteContract.CONTENT_URI, "" + id);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return resultUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = mDBHelper.getWritableDatabase();
        int rowsDeleted;
        switch (uriType) {

            case CODE_NOTE:
                rowsDeleted = sqlDB.delete(NoteContract.PATH, selection, selectionArgs);
                break;

            case CODE_NOTE_ID:
                String id = uri.getLastPathSegment();
                if (selection == null) {
                    selection = NoteContract.COLUMN_ID + " = " + id;
                } else {
                    selection += " AND " + NoteContract.COLUMN_ID + " = " + id;
                }
                rowsDeleted = sqlDB.delete(NoteContract.PATH, selection, selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = mDBHelper.getWritableDatabase();
        int rowsUpdated;
        switch (uriType) {
            case CODE_NOTE_ID:
                String id = uri.getLastPathSegment();

                if (selection == null) {
                    selection = NoteContract.COLUMN_ID + "=" + id;
                } else {
                    selection += " AND " + NoteContract.COLUMN_ID + "=" + id;
                }
                rowsUpdated = sqlDB.update(NoteContract.PATH,
                        values,
                        selection,
                        selectionArgs);
                break;

            case CODE_NOTE:
                rowsUpdated = sqlDB.update(NoteContract.PATH,
                        values,
                        selection,
                        selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }
}
