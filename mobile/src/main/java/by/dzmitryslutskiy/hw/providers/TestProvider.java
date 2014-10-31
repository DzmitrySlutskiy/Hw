package by.dzmitryslutskiy.hw.providers;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.util.Log;

import java.util.Arrays;

import by.dzmitryslutskiy.hw.providers.Contracts.NoteContract;
import by.dzmitryslutskiy.hw.providers.Contracts.TestProviderContract;
import by.dzmitryslutskiy.hw.providers.Contracts.UserContract;

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
    private static final int CODE_NOTE_ID_NEG = 21;

    private static final int CODE_USER = 30;
    private static final int CODE_USER_ID = 40;
    private static final int CODE_USER_ID_NEG = 41;

    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private static final Object mLocker;
    private DBHelper mDBHelper;

    static {
        mLocker = new Object();
        //for NoteContract
        sURIMatcher.addURI(TestProviderContract.AUTHORITY, NoteContract.PATH, CODE_NOTE);
        sURIMatcher.addURI(TestProviderContract.AUTHORITY, NoteContract.PATH + "/#", CODE_NOTE_ID);
        sURIMatcher.addURI(TestProviderContract.AUTHORITY, NoteContract.PATH + "/*", CODE_NOTE_ID_NEG);
        //for UserContract
        sURIMatcher.addURI(TestProviderContract.AUTHORITY, UserContract.PATH, CODE_USER);
        sURIMatcher.addURI(TestProviderContract.AUTHORITY, UserContract.PATH + "/#", CODE_USER_ID);
        sURIMatcher.addURI(TestProviderContract.AUTHORITY, UserContract.PATH + "/*", CODE_USER_ID_NEG);
    }


    public TestProvider() {/*   code    */}

    @Override
    public boolean onCreate() {
        initDBHelperInstance();
        return false;
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        String tableName = getTableNameByUri(uri);
        selection = buildSelection(uri, selection);
        selectionArgs = buildSelectionArgs(uri, selectionArgs);

        try {
            Cursor cursor = mDBHelper.query(tableName, projection,
                    selection, selectionArgs, null, null, sortOrder);

            cursor.setNotificationUri(getContext().getContentResolver(), uri);

            return cursor;
        } catch (Exception e) {
            Log.e(LOG_TAG, "query return exception: " + e);

            return getErrorMatrixCursor(e);
        }
    }


    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        String tableName = getTableNameByUri(uri);

        long id = mDBHelper.insert(tableName, null, values);

        getContext().getContentResolver().notifyChange(uri, null);

        return Uri.withAppendedPath(TestProviderContract.AUTHORITY_URI, "/" + tableName + id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        int rowsDeleted = mDBHelper.delete(getTableNameByUri(uri),
                buildSelection(uri, selection),
                buildSelectionArgs(uri, selectionArgs));

        getContext().getContentResolver().notifyChange(uri, null);

        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        int rowsUpdated = (int) mDBHelper.update(getTableNameByUri(uri),
                values,
                buildSelection(uri, selection),
                buildSelectionArgs(uri, selectionArgs));

        getContext().getContentResolver().notifyChange(uri, null);

        return rowsUpdated;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        if (values == null) {
            return 0;
        }

        mDBHelper.insert(getTableNameByUri(uri), null, values);
        getContext().getContentResolver().notifyChange(uri, null);

        return values.length;
    }

    private String getTableNameByUri(Uri uri) {
        int matchCode = sURIMatcher.match(uri);

        if (matchCode == UriMatcher.NO_MATCH) {
            throw new IllegalArgumentException("Unknown URI: " + uri);
        } else {
            return getTableNameByUri(matchCode);
        }
    }

    private String getTableNameByUri(int matchCode) {
        switch (matchCode) {

            case CODE_NOTE_ID_NEG:
            case CODE_NOTE_ID:
            case CODE_NOTE:
                return NoteContract.PATH;

            case CODE_USER:
            case CODE_USER_ID:
            case CODE_USER_ID_NEG:
                return UserContract.PATH;

//            case UriMatcher.NO_MATCH:
//                throw new IllegalArgumentException("Unknown URI");

            default:
                throw new IllegalArgumentException("URI code not implemented: " + matchCode);
        }
    }

    /**
     * initialize DBHelper instance (thread safe)
     */
    private void initDBHelperInstance() {
        synchronized (mLocker) {
            if (mDBHelper == null) {
                mDBHelper = new DBHelper(getContext());
            }
        }
    }

    /**
     * concatenate string selection and " AND table.idField = ?"
     *
     * @param selection selection
     * @param table     table name
     * @param idField   field ID name (default must be "_id")
     * @return selection with "table.idField = ?"
     */
    private String buildSelection(String selection, String table, String idField) {
        if (selection == null) {
            selection = table + "." + idField + " = ?";
        } else {
            selection += " AND " + table + "." + idField + " = ?";
        }
        return selection;
    }

    /**
     * if current uri use ID - add this ID to selection as COLUMN_ID = ?
     * need add this ID as selectionArgs
     *
     * @param uri       uri
     * @param selection default selection
     * @return rebuilt selection string
     */
    private String buildSelection(Uri uri, String selection) {
        switch (sURIMatcher.match(uri)) {
            case CODE_USER:
            case CODE_NOTE:
                return selection;   //no ID for adding

            case CODE_USER_ID:
            case CODE_USER_ID_NEG:  //need add ID for selection condition
                return buildSelection(selection, UserContract.PATH,
                        TestProviderContract.COLUMN_ID_DEFAULT);

            case CODE_NOTE_ID:
            case CODE_NOTE_ID_NEG:
                return buildSelection(selection, NoteContract.PATH,
                        TestProviderContract.COLUMN_ID_DEFAULT);

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    /**
     * if current uri use ID - add this id to selectionArgs
     *
     * @param uri           uri
     * @param selectionArgs selectionArgs
     * @return selectionArgs with added ID
     */
    private String[] buildSelectionArgs(Uri uri, String[] selectionArgs) {
        switch (sURIMatcher.match(uri)) {
            case CODE_USER:
            case CODE_NOTE:
                return selectionArgs;

            default:        //if ID used
                if (selectionArgs != null) {
                    selectionArgs = Arrays.copyOf(selectionArgs, selectionArgs.length + 1);
                } else {
                    selectionArgs = new String[1];
                }
                selectionArgs[selectionArgs.length - 1] = uri.getLastPathSegment();

        }
        return selectionArgs;
    }

    /**
     * Build MatrixCursor with column {@code TestProviderContract.ERROR_DESCRIPTION} and save
     * in first row error description from {@code exception}
     *
     * @param exception exception for saving
     * @return MatrixCursor with error description
     */
    private MatrixCursor getErrorMatrixCursor(Exception exception) {
        MatrixCursor matrixCursor = new MatrixCursor(
                new String[]{TestProviderContract.ERROR_DESCRIPTION});
        matrixCursor.addRow(new String[]{exception.getMessage()});

        return matrixCursor;
    }
}
