package by.dzmitryslutskiy.hw.bo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Classname
 * Version information
 * 11.11.2014
 * Created by Dzmitry Slutskiy.
 */
public class Country implements Parcelable {
    private static final String ICON = "icon";
    private static final String NAME = "name";
    private static final String FULL_NAME = "fullname";


    public Country() {/*   code    */}

    private String icon;
    private String name;
    private String fullname;


    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(icon);
        dest.writeString(name);
        dest.writeString(fullname);
    }

    public static final Parcelable.Creator<Country> CREATOR
            = new Parcelable.Creator<Country>() {
        public Country createFromParcel(Parcel in) {
            return new Country(in);
        }

        public Country[] newArray(int size) {
            return new Country[size];
        }
    };

    private Country(Parcel in) {
        icon = in.readString();
        name = in.readString();
        fullname = in.readString();
    }
}