package by.dzmitryslutskiy.hw.providers;

import android.database.Cursor;
import android.net.Uri;
import android.test.ApplicationTestCase;

import by.dzmitryslutskiy.hw.CoreApplication;
import by.dzmitryslutskiy.hw.providers.Contracts.NoteContract;

public class TestProviderTest extends ApplicationTestCase<CoreApplication> {


    public TestProviderTest(Class<CoreApplication> applicationClass) {
        super(applicationClass);
    }

    @Override
    protected void setUp() throws Exception {
        createApplication();
        super.setUp();
    }


    public void testQuery() throws Exception {

        Cursor cursor = getContext().getContentResolver().query(NoteContract.CONTENT_URI,
                NoteContract.defaultColumns, null, null, null);

        assertEquals(cursor.getCount(), 9);

        cursor = getContext().getContentResolver().query(
                Uri.withAppendedPath(NoteContract.CONTENT_URI, Integer.toString(6)),
                NoteContract.defaultColumns, null, null, null);

        assertEquals(cursor.getCount(), 1);
    }

    public void testGetType() throws Exception {

    }

    public void testInsert() throws Exception {

    }

    public void testDelete() throws Exception {

    }

    public void testUpdate() throws Exception {

    }

    public void testBulkInsert() throws Exception {

    }
}