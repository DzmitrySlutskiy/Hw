package by.dzmitryslutskiy.hw.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * Classname
 * Version information
 * 22.10.2014
 * Created by Dzmitry Slutskiy.
 */
public class IOUtils {


    private IOUtils() {/*   code    */}


    public static void close(Closeable in) {
        if (in != null) {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
