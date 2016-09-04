package com.example.scame.savealifenotifier.presentation.models;

import android.os.Parcel;
import android.os.Parcelable;


public class DriversMessageModel implements Parcelable {

    private String message;

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    protected DriversMessageModel(Parcel in) { }

    public static final Creator<DriversMessageModel> CREATOR = new Creator<DriversMessageModel>() {
        @Override
        public DriversMessageModel createFromParcel(Parcel in) {
            return new DriversMessageModel(in);
        }

        @Override
        public DriversMessageModel[] newArray(int size) {
            return new DriversMessageModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
