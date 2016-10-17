package com.example.scame.savealifenotifier.firebase;

import android.util.Log;

import com.example.scame.savealifenotifier.SaveAlifeApp;
import com.example.scame.savealifenotifier.data.repository.IFirebaseTokenManager;
import com.example.scame.savealifenotifier.data.repository.IMessagesDataManager;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {

    private static final String TAG = "logTokenRefresh";

    @Inject
    IFirebaseTokenManager firebaseTokenManager;

    @Inject
    IMessagesDataManager messagesDataManager;

    public MyFirebaseInstanceIdService() {
        SaveAlifeApp.getAppComponent().inject(this);
    }

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.i(TAG, "Refreshed token: " + refreshedToken);

        SaveAlifeApp.getAppComponent().inject(this);
        cacheToken(refreshedToken);

        if (!firebaseTokenManager.getOldToken().equals("")) {
            messagesDataManager
                    .sendUpdateTokenRequest()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<ResponseBody>() {
                        @Override
                        public void onCompleted() {
                            Log.i("onxCompleted", "true");
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.i("onxError", e.getLocalizedMessage());
                        }

                        @Override
                        public void onNext(ResponseBody responseBody) {
                            Log.i("onxNext", "next");
                        }
                    });
        } else {
            messagesDataManager
                    .sendRegistrationRequest()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<ResponseBody>() {
                        @Override
                        public void onCompleted() {
                            Log.i("onxCompleted", "true");
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.i("onxError", e.getLocalizedMessage());
                        }

                        @Override
                        public void onNext(ResponseBody responseBody) {
                            Log.i("onxNext", "next");
                        }
                    });
        }
    }


    private void cacheToken(String token) {
        firebaseTokenManager.saveOldToken(firebaseTokenManager.getActiveToken());
        firebaseTokenManager.saveRefreshedToken(token);
    }
}
