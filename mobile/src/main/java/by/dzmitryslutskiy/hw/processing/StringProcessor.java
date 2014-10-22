package by.dzmitryslutskiy.hw.processing;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import by.dzmitryslutskiy.hw.utils.IOUtils;

/**
 * Classname
 * Version information
 * 21.10.2014
 * Created by Dzmitry Slutskiy.
 */
public class StringProcessor implements Processor<String, InputStream> {
    @Override
    public String process(InputStream inputStream) throws Exception {
        InputStreamReader inputStreamReader = null;
        BufferedReader in = null;
        try {
            inputStreamReader = new InputStreamReader(inputStream);
            in = new BufferedReader(inputStreamReader);
            String str;
            StringBuilder builder = new StringBuilder();
            while ((str = in.readLine()) != null) {
                builder.append(str);
            }
            return builder.toString();
        } finally {
            IOUtils.close(in);
            IOUtils.close(inputStreamReader);
            IOUtils.close(inputStream);
        }
    }
}