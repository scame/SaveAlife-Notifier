package com.example.scame.savealifenotifier.presentation.models;


import android.os.Parcel;
import android.os.Parcelable;

public class HelpMessageModel implements Parcelable {

    private double latitude;

    private double longitude;

    private String message;

    public HelpMessageModel() { }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
        dest.writeString(this.message);
    }

    protected HelpMessageModel(Parcel in) {
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
        this.message = in.readString();
    }

    public static final Parcelable.Creator<HelpMessageModel> CREATOR = new Parcelable.Creator<HelpMessageModel>() {
        @Override
        public HelpMessageModel createFromParcel(Parcel source) {
            return new HelpMessageModel(source);
        }

        @Override
        public HelpMessageModel[] newArray(int size) {
            return new HelpMessageModel[size];
        }
    };
}
