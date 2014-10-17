package by.dzmitryslutskiy.hw.data;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Classname
 * Version information
 * 17.10.2014
 * Created by Dzmitry Slutskiy.
 */
public class DataSource {

    private static final List<TypeA> DATA;
    private static int index[] = new int[]{
            android.R.drawable.ic_delete,
            android.R.drawable.ic_btn_speak_now,
            android.R.drawable.ic_input_add,
            android.R.drawable.ic_input_get,
            android.R.drawable.ic_lock_silent_mode,
            android.R.drawable.btn_star_big_on,
            android.R.drawable.ic_menu_compass,
            android.R.drawable.ic_lock_silent_mode,
            android.R.drawable.ic_partial_secure,
            android.R.drawable.ic_secure
    };

    static {
        DATA = new ArrayList<TypeA>();
    }

    private static void initData() throws InterruptedException {
        TimeUnit.SECONDS.sleep(3);
        for (int i = 0; i < 50; i++) {
            DATA.add(TypeFactory.getNewItem(index[i % 10],
                    "test value " + i,
                    "text [" + i + "]",
                    index[(i + 1) % 10]));
        }
    }

    public static List<TypeA> getData() throws Exception {
        initData();
        return DATA;
    }

}