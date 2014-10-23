package by.dzmitryslutskiy.hw.bo;

import org.json.JSONObject;

/**
 * Classname
 * Version information
 * 23.10.2014
 * Created by Dzmitry Slutskiy.
 */
public interface CreateObject<T extends JsonObjectWrapper> {

    public T createObject(JSONObject jsonObject);
}
