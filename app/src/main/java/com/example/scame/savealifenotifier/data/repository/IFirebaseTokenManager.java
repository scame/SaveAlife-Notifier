package com.example.scame.savealifenotifier.data.repository;


public interface IFirebaseTokenManager {

    void saveRefreshedToken(String token);

    void saveOldToken(String token);

    String getActiveToken();

    String getOldToken();
}
