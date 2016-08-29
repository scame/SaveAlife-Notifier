package com.example.scame.savealifenotifier.data.repository;


import com.example.scame.savealifenotifier.SaveAlifeApp;
import com.example.scame.savealifenotifier.data.api.HelpMessagesApi;

import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import rx.Observable;

public class MessagesDataManagerImp implements IMessagesDataManager {

    private Retrofit retrofit;
    private HelpMessagesApi helpMessagesApi;

    public MessagesDataManagerImp() {
        retrofit = SaveAlifeApp.getAppComponent().getRetrofit();
        helpMessagesApi = retrofit.create(HelpMessagesApi.class);
    }

    @Override
    public Observable<ResponseBody> sendHelpMeMessage(String message) {

        return helpMessagesApi.sendHelpMessage(message);
    }
}
