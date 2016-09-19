package com.example.scame.savealifenotifier.data.entities;

public class LocationMessageEntity {

    private String currentToken;

    private double currentLat;

    private double currentLon;

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
}
