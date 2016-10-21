package com.example.scame.savealifenotifier.data.entities.mapbox;


public class RouteEntity {

    private String geometry;

    private double duration;

    private double distance;

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
