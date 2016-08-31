package com.example.scame.savealifenotifier.data.repository;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.scame.savealifenotifier.R;
import com.example.scame.savealifenotifier.SaveAlifeApp;
import com.example.scame.savealifenotifier.data.api.ServerApi;
import com.example.scame.savealifenotifier.data.entities.LatLongPair;
import com.example.scame.savealifenotifier.data.entities.ServerMessageEntity;

import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import rx.Observable;

public class MessagesDataManagerImp implements IMessagesDataManager {

    private Retrofit retrofit;
    private ServerApi serverApi;

    private Context context;
    private SharedPreferences sharedPrefs;

    private IFirebaseTokenManager tokenManager;

    public MessagesDataManagerImp() {
        retrofit = SaveAlifeApp.getAppComponent().getRetrofit();
        serverApi = retrofit.create(ServerApi.class);
        tokenManager = new FirebaseTokenManagerImp();

        context = SaveAlifeApp.getAppComponent().getApp();
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Override
    public Observable<ResponseBody> sendLocationMessage() {
        return serverApi.sendLocationToServer(createLocationEntity());
    }

    @Override
    public Observable<ResponseBody> sendHelpMeMessage(String helpMessage) {
        return serverApi.sendHelpMessage(createHelpMeEntity(helpMessage));
    }


    @Override
    public Observable<ResponseBody> sendDestinationMessage(LatLongPair latLongPair) {
        return serverApi.sendLocationToServer(createDestinationEntity(latLongPair));
    }


    @Override
    public Observable<ResponseBody> sendRegistrationRequest() {
        ServerMessageEntity messageEntity = new ServerMessageEntity();
        messageEntity.setCurrentToken(tokenManager.getActiveToken());

        return serverApi.registrationRequest(messageEntity);
    }


    @Override
    public Observable<ResponseBody> sendUpdateTokenRequest() {
        ServerMessageEntity messageEntity = new ServerMessageEntity();

        messageEntity.setCurrentToken(tokenManager.getActiveToken());
        messageEntity.setOldToken(tokenManager.getOldToken());

        return serverApi.tokenUpdateRequest(messageEntity);
    }

    @Override
    public Observable<ResponseBody> sendChangeStatusRequest(String status) {
        ServerMessageEntity messageEntity = new ServerMessageEntity();

        messageEntity.setCurrentToken(tokenManager.getActiveToken());
        messageEntity.setRole(status);

        return serverApi.changeStatus(messageEntity);
    }

    private ServerMessageEntity createLocationEntity() {
        double latitude = Double.valueOf(sharedPrefs.getString(context.getString(R.string.current_latitude), ""));
        double longitude = Double.valueOf(sharedPrefs.getString(context.getString(R.string.current_longitude), ""));

        ServerMessageEntity messageEntity = new ServerMessageEntity();
        messageEntity.setCurrentToken(tokenManager.getActiveToken());
        messageEntity.setCurrentLat(latitude);
        messageEntity.setCurrentLon(longitude);

        return messageEntity;
    }

    private ServerMessageEntity createDestinationEntity(LatLongPair latLongPair) {
        ServerMessageEntity messageEntity = createLocationEntity();
        messageEntity.setRole("driver");
        messageEntity.setDestinationLon(latLongPair.getLongitude());
        messageEntity.setDestinationLat(latLongPair.getLatitude());

        return messageEntity;
    }

    private ServerMessageEntity createHelpMeEntity(String message) {
        ServerMessageEntity messageEntity = createLocationEntity();
        messageEntity.setMessage(message);
        messageEntity.setRole("person");

        return messageEntity;
    }
}
