package com.example.scame.savealifenotifier.data.repository;


import okhttp3.ResponseBody;
import rx.Observable;

public interface IMessagesDataManager {

    Observable<ResponseBody> sendHelpMeMessage(String message);
}
