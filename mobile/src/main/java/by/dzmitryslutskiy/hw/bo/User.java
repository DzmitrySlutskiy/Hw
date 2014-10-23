package by.dzmitryslutskiy.hw.bo;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONObject;

/**
 * Classname
 * Version information
 * 22.10.2014
 * Created by Dzmitry Slutskiy.
 */
public class User extends JsonObjectWrapper {

    private static final String NAME = "name";
    private static final String ID = "id";

    public User(String jsonObject) {
        super(jsonObject);
    }

    public User(JSONObject jsonObject) {
        super(jsonObject);
    }

    public String getName() {
        return getString(NAME);
    }

    public Long getId() {
        return getLong(ID);
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(getId());
        dest.writeString(getName());
        Log.d("User", "write to parcel");
    }

    public static final Parcelable.Creator<User> CREATOR
            = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public User(Parcel in) {
        super("{\"" + ID + "\":" + in.readLong() +
                ",\"" + NAME + "\":\"" + in.readString() +
                "\"}");
        Log.d("User","read from parcel");
    }
}