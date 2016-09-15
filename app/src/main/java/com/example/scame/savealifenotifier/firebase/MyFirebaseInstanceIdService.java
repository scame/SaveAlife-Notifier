package com.example.scame.savealifenotifier.firebase;

import android.util.Log;

import com.example.scame.savealifenotifier.SaveAlifeApp;
import com.example.scame.savealifenotifier.data.repository.FirebaseTokenManagerImp;
import com.example.scame.savealifenotifier.data.repository.IFirebaseTokenManager;
import com.example.scame.savealifenotifier.data.repository.IMessagesDataManager;
import com.example.scame.savealifenotifier.data.repository.MessagesDataManagerImp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {

    private static final String TAG = "logTokenRefresh";

    private IFirebaseTokenManager tokenManager;

    private IMessagesDataManager messagesDataManager;

    public MyFirebaseInstanceIdService() {
        tokenManager = new FirebaseTokenManagerImp();
        messagesDataManager = new MessagesDataManagerImp();
    }

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.i(TAG, "Refreshed token: " + refreshedToken);

        SaveAlifeApp.getAppComponent().inject(this);
        cacheToken(refreshedToken);

        if (!tokenManager.getOldToken().equals("")) {
            messagesDataManager
                    .sendUpdateTokenRequest()
                    .subscribe(responseBody -> Log.i("onxTokenUpdate", "updated"));
        } else {
            messagesDataManager
                    .sendRegistrationRequest()
                    .subscribe(responseBody -> Log.i("onxTokenSending", "sent"));
        }
    }


    private void cacheToken(String token) {
        tokenManager.saveOldToken(tokenManager.getActiveToken());
        tokenManager.saveRefreshedToken(token);
    }
}
