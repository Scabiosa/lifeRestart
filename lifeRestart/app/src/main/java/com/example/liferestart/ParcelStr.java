package com.example.liferestart;

import android.os.Parcel;
import android.os.Parcelable;

public class ParcelStr implements Parcelable {


    public ParcelStr(String ageInfo, String eventsInfo, String talentsInfo) {
        this.ageInfo = ageInfo;
        this.eventsInfo = eventsInfo;
        this.talentsInfo = talentsInfo;
    }

    private String ageInfo;

    public String getAgeInfo() {
        return ageInfo;
    }

    public String getEventsInfo() {
        return eventsInfo;
    }

    private String eventsInfo;

    public String getTalentsInfo() {
        return talentsInfo;
    }

    private String talentsInfo;

    protected ParcelStr(Parcel in) {
        ageInfo = in.readString();
        eventsInfo = in.readString();
        talentsInfo = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ageInfo);
        dest.writeString(eventsInfo);
        dest.writeString(talentsInfo);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ParcelStr> CREATOR = new Creator<ParcelStr>() {
        @Override
        public ParcelStr createFromParcel(Parcel in) {
            return new ParcelStr(in);
        }

        @Override
        public ParcelStr[] newArray(int size) {
            return new ParcelStr[size];
        }
    };
}
