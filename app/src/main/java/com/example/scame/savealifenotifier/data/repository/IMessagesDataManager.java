package com.example.scame.savealifenotifier.data.repository;


import com.example.scame.savealifenotifier.data.entities.LatLongPair;

import okhttp3.ResponseBody;
import rx.Observable;

public interface IMessagesDataManager {

    Observable<ResponseBody> sendHelpMeMessage(String helpMessage);

    Observable<ResponseBody> sendLocationMessage();

    Observable<ResponseBody> sendDestinationMessage(LatLongPair latLongPair);

    Observable<ResponseBody> sendUpdateTokenRequest();

    Observable<ResponseBody> sendChangeStatusRequest(String status);

    Observable<ResponseBody> sendRegistrationRequest();
}
