package com.example.scame.savealifenotifier.data.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class LatLongPair implements Parcelable {

    private double latitude;
    private double longitude;

    public LatLongPair(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
    }

    protected LatLongPair(Parcel in) {
        latitude = in.readDouble();
        longitude = in.readDouble();
    }

    public static final Creator<LatLongPair> CREATOR = new Creator<LatLongPair>() {
        @Override
        public LatLongPair createFromParcel(Parcel in) {
            return new LatLongPair(in);
        }

        @Override
        public LatLongPair[] newArray(int size) {
            return new LatLongPair[size];
        }
    };

}