package com.example.scame.savealifenotifier.presentation.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.scame.savealifenotifier.data.entities.LatLongPair;

import java.util.ArrayList;
import java.util.List;


public class DriversMessageModel implements Parcelable {

    private String messageBody;

    private List<LatLongPair> path;

    public DriversMessageModel() {
        path = new ArrayList<>();
    }

    public void setPath(List<LatLongPair> path) {
        this.path = path;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public List<LatLongPair> getPath() {
        return path;
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
