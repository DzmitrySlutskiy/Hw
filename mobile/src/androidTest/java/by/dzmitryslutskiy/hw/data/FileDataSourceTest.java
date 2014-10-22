package by.dzmitryslutskiy.hw.data;

import android.test.ApplicationTestCase;

import java.io.FileNotFoundException;

import by.dzmitryslutskiy.hw.CoreApplication;
import by.dzmitryslutskiy.hw.R;
import by.dzmitryslutskiy.hw.processing.StringProcessor;

public class FileDataSourceTest extends ApplicationTestCase<CoreApplication> {

    public FileDataSourceTest() {
        super(CoreApplication.class);
    }

    @Override
    protected void setUp() throws Exception {
        createApplication();
        super.setUp();
    }

    public void testAsset() throws Exception {
        StringProcessor stringProcessor = new StringProcessor();

        FileDataSource fileDataSourceAsset = FileDataSource.get(getContext(), FileDataSource.FileType.ASSET);

        assertEquals("sample data in assets",
                stringProcessor.process(fileDataSourceAsset.getResult("test_asset.txt")));
    }

    public void testRawString() throws Exception {
        StringProcessor stringProcessor = new StringProcessor();
        FileDataSource fileDataSourceAsset = FileDataSource.get(getContext(), FileDataSource.FileType.RAW);
        assertEquals("sample raw data",
                stringProcessor.process(fileDataSourceAsset.getResult("" + R.raw.test_raw)));
    }

    public void testRawInt() throws Exception {
        StringProcessor stringProcessor = new StringProcessor();
        FileDataSource fileDataSourceAsset = FileDataSource.get(getContext(), FileDataSource.FileType.RAW);
        assertEquals("sample raw data",
                stringProcessor.process(fileDataSourceAsset.getResult(R.raw.test_raw)));
    }

    public void testInvalidFileName() {
        StringProcessor stringProcessor = new StringProcessor();
        FileDataSource fileDataSourceAsset = FileDataSource.get(getContext(), FileDataSource.FileType.EXTERNAL);
        try {
            stringProcessor.process(fileDataSourceAsset.getResult("this file not have"));
        } catch (Exception e) {
            assertTrue(e instanceof FileNotFoundException);
        }

    }
}