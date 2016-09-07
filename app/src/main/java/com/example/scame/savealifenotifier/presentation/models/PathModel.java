package com.example.scame.savealifenotifier.presentation.models;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class PathModel implements Parcelable {

    private List<LatLng> path;

    public void setPath(List<LatLng> path) {
        this.path = path;
    }

    public List<LatLng> getPath() {
        return path;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    protected PathModel(Parcel in) {
        path = in.createTypedArrayList(LatLng.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(path);
    }

    public static final Creator<PathModel> CREATOR = new Creator<PathModel>() {
        @Override
        public PathModel createFromParcel(Parcel in) {
            return new PathModel(in);
        }

        @Override
        public PathModel[] newArray(int size) {
            return new PathModel[size];
        }
    };
}
