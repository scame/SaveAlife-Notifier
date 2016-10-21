package com.example.scame.savealifenotifier.data.entities.backend;


public class StatusEntity {

    private String role;

    private String currentToken;

    public void setRole(String role) {
        this.role = role;
    }

    public void setCurrentToken(String currentToken) {
        this.currentToken = currentToken;
    }

    public String getCurrentToken() {
        return currentToken;
    }

    public String getRole() {
        return role;
    }
}
