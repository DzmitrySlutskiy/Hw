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
public class Note extends JsonObjectWrapper {

    private static final String TITLE = "title";
    private static final String CONTENT = "content";
    private static final String ID = "id";

    public Note(String jsonObject) {
        super(jsonObject);
    }

    public Note(JSONObject jsonObject) {
        super(jsonObject);
    }

    public String getTitle() {
        return getString(TITLE);
    }

    public String getContent() {
        return getString(CONTENT);
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
        dest.writeString(getTitle());
        dest.writeString(getContent());
        Log.d("Note", "write to parcel");
    }

    public static final Parcelable.Creator<Note> CREATOR
            = new Parcelable.Creator<Note>() {
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public Note(Parcel in) {
        super("{\"" + ID + "\":" + in.readLong() + "," +
                "\"" + TITLE + "\":\"" + in.readString() + "\"," +
                "\"" + CONTENT + "\":\"" + in.readString() + "\"" +
                "}");
        Log.d("Note", "read from parcel");
    }
}