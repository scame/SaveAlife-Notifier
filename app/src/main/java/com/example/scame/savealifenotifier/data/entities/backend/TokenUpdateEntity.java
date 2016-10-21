package com.example.scame.savealifenotifier.data.entities.backend;


public class TokenUpdateEntity {

    private String currentToken;

    private String oldToken;

    public void setCurrentToken(String currentToken) {
        this.currentToken = currentToken;
    }

    public void setOldToken(String oldToken) {
        this.oldToken = oldToken;
    }

    public String getCurrentToken() {
        return currentToken;
    }

    public String getOldToken() {
        return oldToken;
    }
}
