package by.dzmitryslutskiy.hw.providers;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import by.dzmitryslutskiy.hw.providers.Contracts.NoteContract;
import by.dzmitryslutskiy.hw.providers.Contracts.TestProviderContract;
import by.dzmitryslutskiy.hw.providers.Contracts.UserContract;

/**
 * TestProvider
 * Version information
 * 28.10.2014
 * Created by Dzmitry Slutskiy.
 */
public class TestProvider extends ContentProvider {

    private static final String LOG_TAG = TestProvider.class.getSimpleName();

    private static final int CODE_TABLE = 1;
    private static final int CODE_ID = 2;
    private static final int CODE_ID_NEG = 3;

    private static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/";
    private static final String CONTENT_DIR_TYPE = "vnd.android.cursor.dir/";

    private static final UriMatcher URI_MATCHER;

    private static final Object DB_LOCKER;
    private static DBHelper sDBHelper;
    private static final List<String> implementedTables;

    static {
        DB_LOCKER = new Object();

        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

        URI_MATCHER.addURI(TestProviderContract.AUTHORITY, "*", CODE_TABLE);
        URI_MATCHER.addURI(TestProviderContract.AUTHORITY, "*/#", CODE_ID);
        URI_MATCHER.addURI(TestProviderContract.AUTHORITY, "*/*", CODE_ID_NEG);

        implementedTables = new ArrayList<String>();
        implementedTables.add(NoteContract.PATH);
        implementedTables.add(UserContract.PATH);
    }


    public TestProvider() {/*   code    */}

    @Override
    public boolean onCreate() {
        initDBHelperInstance();
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {

        int matchCode = matchUri(uri);
        String tableName = getTableNameByUriCode(matchCode, uri);
        try {
            Cursor cursor = sDBHelper.query(tableName,
                    projection,
                    buildSelection(matchCode, tableName, selection),
                    buildSelectionArgs(matchCode, uri, selectionArgs),
                    null, null, sortOrder);

            cursor.setNotificationUri(getContext().getContentResolver(), uri);

            return cursor;
        } catch (Exception e) {
            Log.e(LOG_TAG, "query return exception: " + e);

            return getErrorMatrixCursor(e);
        }
    }


    @Override
    public String getType(Uri uri) {
        int matchCode = matchUri(uri);
        String tableName = getTableNameByUriCode(matchCode, uri);

        if (matchCode == CODE_TABLE) {
            return CONTENT_DIR_TYPE + tableName;
        } else {
            return CONTENT_ITEM_TYPE + tableName;
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        String tableName = getTableNameByUriCode(matchUri(uri), uri);

        long id = sDBHelper.insert(tableName, null, values);
        getContext().getContentResolver().notifyChange(uri, null);

        return Uri.withAppendedPath(TestProviderContract.AUTHORITY_URI, "/" + tableName + "/" + id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int matchCode = matchUri(uri);
        String tableName = getTableNameByUriCode(matchCode, uri);

        int rowsDeleted = sDBHelper.delete(tableName,
                buildSelection(matchCode, tableName, selection),
                buildSelectionArgs(matchCode, uri, selectionArgs));

        getContext().getContentResolver().notifyChange(uri, null);

        return rowsDeleted;
    }


    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int matchedUriCode = matchUri(uri);
        String tableName = getTableNameByUriCode(matchedUriCode, uri);

        int rowsUpdated = (int) sDBHelper.update(tableName,
                values,
                buildSelection(matchedUriCode, tableName, selection),
                buildSelectionArgs(matchedUriCode, uri, selectionArgs));

        getContext().getContentResolver().notifyChange(uri, null);

        return rowsUpdated;
    }

    @Override
    public int bulkInsert(Uri uri, @NonNull ContentValues[] values) {

        sDBHelper.insert(getTableNameByUriCode(matchUri(uri), uri), null, values);
        getContext().getContentResolver().notifyChange(uri, null);

        return values.length;
    }

    private String getTableNameByUriCode(int matchCode, Uri uri) {
        String tableName;
        if (matchCode == CODE_TABLE) {
            tableName = uri.getLastPathSegment();
        } else {             //ID used in URI
            List<String> listSegments = uri.getPathSegments();

            //uri=content://AUTHORITY/Note/1
            //in list item[0] = Note, item[1] = 1, size=2, -2 for get TableName segment
            tableName = listSegments.get(listSegments.size() - 2);
        }

        //необязательная проверка, но тогда будет Exception от SQLite при выполнении запроса
        if (! implementedTables.contains(tableName)) {
            throw new IllegalArgumentException("Table \"" + tableName + "\" not exists");
        }

        return tableName;
    }

    /**
     * initialize DBHelper instance (thread safe)
     */
    private void initDBHelperInstance() {
        synchronized (DB_LOCKER) {
            if (sDBHelper == null) {
                sDBHelper = new DBHelper(getContext());
            }
        }
    }

    /**
     * if current uri use ID - add this ID to selection as COLUMN_ID = ?
     * need add this ID as selectionArgs
     *
     * @param matchedUriCode uri code
     * @param tableName      table name
     * @param selection      default selection
     * @return rebuilt selection string
     */
    private String buildSelection(int matchedUriCode, String tableName, String selection) {

        if (matchedUriCode == CODE_TABLE) {
            return selection;
        }

        if (selection == null) {
            selection = tableName + "." + TestProviderContract.COLUMN_ID_DEFAULT + " = ?";
        } else {
            selection += " AND " + tableName + "." + TestProviderContract.COLUMN_ID_DEFAULT + " = ?";
        }
        return selection;
    }

    /**
     * if current uri use ID - add this id to selectionArgs
     *
     * @param matchedUriCode uri code
     * @param selectionArgs  selectionArgs
     * @return selectionArgs with added ID
     */
    private String[] buildSelectionArgs(int matchedUriCode, Uri uri, String[] selectionArgs) {
        if (matchedUriCode == CODE_TABLE) {
            return selectionArgs;
        }

        if (selectionArgs != null) {
            selectionArgs = Arrays.copyOf(selectionArgs, selectionArgs.length + 1);
        } else {
            selectionArgs = new String[1];
        }
        selectionArgs[selectionArgs.length - 1] = uri.getLastPathSegment();

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

    /**
     * Match uri or throw IllegalArgumentException if URI unknown
     *
     * @param uri uri for match
     * @return matched code
     */
    private int matchUri(Uri uri) {
        int matchCode = URI_MATCHER.match(uri);

        if (matchCode == UriMatcher.NO_MATCH) {
            throw new IllegalArgumentException("Unknown URI: " + uri);
        } else {
            return matchCode;
        }
    }
}