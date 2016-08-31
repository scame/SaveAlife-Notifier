package com.example.scame.savealifenotifier.data.entities;

public class ServerMessageEntity {

    private String currentToken;

    private String oldToken;

    private String role;

    private double currentLat;

    private double currentLon;

    private double destinationLat;

    private double destinationLon;

    private boolean enable;

    private String message;

    public void setCurrentToken(String currentToken) {
        this.currentToken = currentToken;
    }

    public void setOldToken(String oldToken) {
        this.oldToken = oldToken;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setCurrentLat(double currentLat) {
        this.currentLat = currentLat;
    }

    public void setCurrentLon(double currentLon) {
        this.currentLon = currentLon;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public String getOldToken() {
        return oldToken;
    }

    public String getRole() {
        return role;
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

    public String getMessage() {
        return message;
    }

    public double getDestinationLat() {
        return destinationLat;
    }

    public double getDestinationLon() {
        return destinationLon;
    }
}
