package com.example.scame.savealifenotifier.firebase;

import android.util.Log;

import com.example.scame.savealifenotifier.SaveAlifeApp;
import com.example.scame.savealifenotifier.data.repository.IFirebaseTokenManager;
import com.example.scame.savealifenotifier.data.repository.IMessagesDataManager;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import javax.inject.Inject;

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {

    private static final String TAG = "logTokenRefresh";

    @Inject IFirebaseTokenManager tokenManager;

    @Inject IMessagesDataManager messagesDataManager;

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.i(TAG, "Refreshed token: " + refreshedToken);

        SaveAlifeApp.getAppComponent().inject(this);
        cacheToken(refreshedToken);

        if (!tokenManager.getOldToken().equals("")) {
            messagesDataManager.sendUpdateTokenRequest();
        }
    }


    private void cacheToken(String token) {
        tokenManager.saveOldToken(tokenManager.getActiveToken());
        tokenManager.saveRefreshedToken(token);
    }
}
