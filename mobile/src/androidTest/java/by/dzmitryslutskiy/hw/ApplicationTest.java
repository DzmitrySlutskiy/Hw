package by.dzmitryslutskiy.hw;

import android.test.ApplicationTestCase;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<CoreApplication> {
    public ApplicationTest() throws Exception {
        super(CoreApplication.class);
        createApplication();
    }
}