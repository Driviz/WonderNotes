package com.driviz.notepad;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Driviz on 19-07-2017.
 */

public class Notes_objects implements Parcelable {
    private int _id;
    private String _file_name;

    public Notes_objects(int id, String file_name) {
        this._id=id;
        this._file_name=file_name;
    }

    public String get_file_name() {
        return _file_name;
    }

    public int get_id() {
        return _id;
    }

    protected Notes_objects(Parcel in) {
        _id = in.readInt();
        _file_name = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(_id);
        dest.writeString(_file_name);
    }


    public static final Parcelable.Creator<Notes_objects> CREATOR = new Parcelable.Creator<Notes_objects>() {
        @Override
        public Notes_objects createFromParcel(Parcel in) {
            return new Notes_objects(in);
        }

        @Override
        public Notes_objects[] newArray(int size) {
            return new Notes_objects[size];
        }
    };

}