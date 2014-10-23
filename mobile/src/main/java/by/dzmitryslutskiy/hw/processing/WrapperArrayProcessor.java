package by.dzmitryslutskiy.hw.processing;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.List;

import by.dzmitryslutskiy.hw.bo.CreateObject;
import by.dzmitryslutskiy.hw.bo.JsonArrayListWrapper;
import by.dzmitryslutskiy.hw.bo.JsonObjectWrapper;

/**
 * Classname
 * Version information
 * 22.10.2014
 * Created by Dzmitry Slutskiy.
 */
public abstract class WrapperArrayProcessor<T extends JsonObjectWrapper> implements Processor<List<T>, InputStream>, CreateObject<T> {

    @Override
    public List<T> process(InputStream inputStream) throws Exception {
        String string = new StringProcessor().process(inputStream);
        JSONArray array = new JSONArray(string);

        return new JsonArrayListWrapper<T>(array, this);
        /*List<T> noteArray = new ArrayList<T>(array.length());
        for (int i = 0; i < array.length(); i++) {
            JSONObject jsonObject = array.getJSONObject(i);
            noteArray.add(createObject(jsonObject));
        }
        return noteArray;*/
    }

    public abstract T createObject(JSONObject jsonObject);
}