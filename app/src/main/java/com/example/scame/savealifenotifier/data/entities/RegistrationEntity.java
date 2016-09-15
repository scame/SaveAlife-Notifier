package com.example.scame.savealifenotifier.data.entities;


public class RegistrationEntity {

    private String currentToken;

    private String role;

    public void setCurrentToken(String currentToken) {
        this.currentToken = currentToken;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCurrentToken() {
        return currentToken;
    }

    public String getRole() {
        return role;
    }
}
