package by.dzmitryslutskiy.hw.providers.Contracts;

import android.net.Uri;

/**
 * Classname
 * Version information
 * 30.10.2014
 * Created by Dzmitry Slutskiy.
 */
public class TestProviderContract {

    public static final String ERROR_DESCRIPTION = "ErrorDescription";
    public static final String AUTHORITY = "by.dzmitryslutskiy.hw.providers.testprovider";

    public static final String COLUMN_ID_DEFAULT = "_id";

    public static final Uri AUTHORITY_URI = Uri.parse("content://" + AUTHORITY);

    private TestProviderContract() {/*   code    */}

}
