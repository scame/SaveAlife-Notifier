package com.example.scame.savealifenotifier.data.entities.backend;


public class DestinationEntity {

    private String currentToken;

    private Double currentLat;

    private Double currentLon;

    private Double destinationLat;

    private Double destinationLon;

    public void setCurrentToken(String currentToken) {
        this.currentToken = currentToken;
    }

    public void setCurrentLat(double currentLat) {
        this.currentLat = currentLat;
    }

    public void setCurrentLon(double currentLon) {
        this.currentLon = currentLon;
    }

    public void setDestinationLat(double destinationLat) {
        this.destinationLat = destinationLat;
    }

    public void setDestinationLon(double destinationLon) {
        this.destinationLon = destinationLon;
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

    public double getDestinationLat() {
        return destinationLat;
    }

    public double getDestinationLon() {
        return destinationLon;
    }
}
