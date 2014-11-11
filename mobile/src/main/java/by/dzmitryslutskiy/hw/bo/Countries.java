package by.dzmitryslutskiy.hw.bo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Countries
 * Version information
 * 11.11.2014
 * Created by Dzmitry Slutskiy.
 */
public class Countries implements Parcelable {

    List<Country> mCountries;

    public Countries(InputStream stream) throws IOException {
        mCountries = readJsonStream(stream);
    }

    public List<Country> readJsonStream(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        Gson gson = new Gson();
        List<Country> list = new ArrayList<Country>();
        try {
            reader.beginArray();
            while (reader.hasNext()) {
                Country message = gson.fromJson(reader, Country.class);
                list.add(message);
            }
            reader.endArray();
        } finally {
            reader.close();
        }
        return list;
    }

    public Country getItem(int index) {
        return mCountries.get(index);
    }

    public int getSize() {
        return mCountries.size();
    }

    public void add(Country country) {
        mCountries.add(country);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (mCountries != null) {
            dest.writeInt(mCountries.size());
            for (Country item : mCountries) {
                dest.writeParcelable(item, Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
            }
        } else {
            dest.writeInt(0);
        }
    }

    public static final Parcelable.Creator<Countries> CREATOR
            = new Parcelable.Creator<Countries>() {
        public Countries createFromParcel(Parcel in) {
            return new Countries(in);
        }

        public Countries[] newArray(int size) {
            return new Countries[size];
        }
    };

    private Countries(Parcel in) {

        int size = in.readInt();
        mCountries = new ArrayList<Country>();
        for (int i = 0; i < size; i++) {
            Country country = in.readParcelable(Country.class.getClassLoader());
            mCountries.add(country);
        }
    }
}
