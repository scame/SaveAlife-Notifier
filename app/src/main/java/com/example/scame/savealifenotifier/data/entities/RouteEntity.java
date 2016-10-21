package com.example.scame.savealifenotifier.data.entities;


import com.google.gson.annotations.SerializedName;

public class RouteEntity {

    private String geometry;

    private double duration;

    private double distance;

    @SerializedName("overview_polyline")
    private PointsEntity overviewPolyline;

    public PointsEntity getOverviewPolyline() {
        return overviewPolyline;
    }

    public void setOverviewPolyline(PointsEntity overviewPolyline) {
        this.overviewPolyline = overviewPolyline;
    }

    public void setGeometry(String geometry) {
        this.geometry = geometry;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getGeometry() {
        return geometry;
    }

    public double getDuration() {
        return duration;
    }

    public double getDistance() {
        return distance;
    }
}
