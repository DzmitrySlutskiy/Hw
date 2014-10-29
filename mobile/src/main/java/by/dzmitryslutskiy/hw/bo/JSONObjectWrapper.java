package by.dzmitryslutskiy.hw.bo;

import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Classname
 * Version information
 * 22.10.2014
 * Created by Dzmitry Slutskiy.
 */
public abstract class JsonObjectWrapper
        implements Parcelable {

    private JSONObject mJO;

    public JsonObjectWrapper(String jsonObject) {
        try {
            mJO = new JSONObject(jsonObject);
        } catch (JSONException e) {
            throw new IllegalArgumentException("invalid json string");
        }
    }

    public JsonObjectWrapper(JSONObject jsonObject) {
        mJO = jsonObject;
    }

    protected String getString(String key) {
        return mJO.optString(key);
    }

    protected Long getLong(String id) {
        return mJO.optLong(id);
    }

    @Override
    public String toString() {
        return mJO.toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

}
