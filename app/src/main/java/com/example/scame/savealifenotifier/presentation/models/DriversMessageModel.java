package com.example.scame.savealifenotifier.presentation.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;


public class DriversMessageModel implements Parcelable {

    private String messageBody;

    private List<LatLng> path;

    public DriversMessageModel() {
        path = new ArrayList<>();
    }

    public void setPath(List<LatLng> path) {
        this.path = path;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public List<LatLng> getPath() {
        return path;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.messageBody);
        dest.writeTypedList(this.path);
    }

    protected DriversMessageModel(Parcel in) {
        this.messageBody = in.readString();
        this.path = in.createTypedArrayList(LatLng.CREATOR);
    }

    public static final Parcelable.Creator<DriversMessageModel> CREATOR = new Parcelable.Creator<DriversMessageModel>() {
        @Override
        public DriversMessageModel createFromParcel(Parcel source) {
            return new DriversMessageModel(source);
        }

        @Override
        public DriversMessageModel[] newArray(int size) {
            return new DriversMessageModel[size];
        }
    };
}
