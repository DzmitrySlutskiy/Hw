package by.dzmitryslutskiy.hw.providers;

import android.net.Uri;
import android.test.ApplicationTestCase;

import junit.framework.TestCase;

import by.dzmitryslutskiy.hw.CoreApplication;
import by.dzmitryslutskiy.hw.providers.Contracts.NoteContract;
import by.dzmitryslutskiy.hw.providers.Contracts.UserContract;

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

        Uri uri = Uri.withAppendedPath(NoteContract.CONTENT_URI,)

        getContext().getContentResolver().query()
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