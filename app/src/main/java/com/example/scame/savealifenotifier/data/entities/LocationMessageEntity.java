package com.example.scame.savealifenotifier.data.entities;

public class LocationMessageEntity {

    private String currentToken;

    private double currentLat;

    private double currentLon;

    private boolean enable;

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public void setCurrentToken(String currentToken) {
        this.currentToken = currentToken;
    }

    public void setCurrentLat(double currentLat) {
        this.currentLat = currentLat;
    }

    public void setCurrentLon(double currentLon) {
        this.currentLon = currentLon;
    }

    public String getCurrentToken() {
        return currentToken;
    }


    public double getCurrentLat() {
        return currentLat;
    }

    public double getCurrentLon() {
        return currentLon;
    }

    public boolean isEnable() {
        return enable;
    }
}
