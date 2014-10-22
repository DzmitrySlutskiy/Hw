package by.dzmitryslutskiy.hw.data;

import android.text.format.DateUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Classname
 * Version information
 * 21.10.2014
 * Created by Dzmitry Slutskiy.
 */
public class ArrayStringDataSource implements DataSource<ArrayList<String>, Void> {

    private static final ArrayList<String> DATA;

    static {
        DATA = new ArrayList<String>();
        for (int i = 0; i < 2; i++) {
            DATA.add("test value " + i);
        }
    }


    public static List<String> getData() throws Exception {
        TimeUnit.MILLISECONDS.sleep(500);
        return (List<String>) DATA.clone();
    }

    @Override
    public ArrayList<String> getResult(Void pVoid) throws Exception {
        return (ArrayList<String>) getData();
    }

}
