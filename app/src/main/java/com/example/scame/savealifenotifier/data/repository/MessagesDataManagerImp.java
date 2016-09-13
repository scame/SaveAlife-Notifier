package com.example.scame.savealifenotifier.data.repository;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.scame.savealifenotifier.R;
import com.example.scame.savealifenotifier.SaveAlifeApp;
import com.example.scame.savealifenotifier.data.api.ServerApi;
import com.example.scame.savealifenotifier.data.entities.DestinationEntity;
import com.example.scame.savealifenotifier.data.entities.HelpMessageEntity;
import com.example.scame.savealifenotifier.data.entities.LatLongPair;
import com.example.scame.savealifenotifier.data.entities.LocationMessageEntity;
import com.example.scame.savealifenotifier.data.entities.StatusEntity;
import com.example.scame.savealifenotifier.data.entities.TokenUpdateEntity;
import com.example.scame.savealifenotifier.presentation.fragments.EndPointFragment;

import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import rx.Observable;

public class MessagesDataManagerImp implements IMessagesDataManager {

    private Retrofit retrofit;
    private ServerApi serverApi;

    private Context context;
    private SharedPreferences sharedPrefs;

    private IFirebaseTokenManager tokenManager;

    private IUserDataManager userDataManager;

    public MessagesDataManagerImp() {
        retrofit = SaveAlifeApp.getAppComponent().getRetrofit();
        serverApi = retrofit.create(ServerApi.class);
        tokenManager = new FirebaseTokenManagerImp();
        userDataManager = new UserDataManagerImp();

        context = SaveAlifeApp.getAppComponent().getApp();
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Override
    public Observable<ResponseBody> sendLocationMessage() {
        LatLongPair latLong = getCurrentLatLong();
        String currentRole = getCurrentMode();

        LocationMessageEntity locationEntity = new LocationMessageEntity();


        locationEntity.setRole(currentRole);
        locationEntity.setCurrentToken(tokenManager.getActiveToken());
        locationEntity.setCurrentLat(latLong.getLatitude());
        locationEntity.setCurrentLon(latLong.getLongitude());

        return serverApi.sendLocationToServer(locationEntity);
    }

    @Override
    public Observable<ResponseBody> sendHelpMeMessage(String helpMessage) {
        LatLongPair latLong = getCurrentLatLong();

        HelpMessageEntity helpMessageEntity = new HelpMessageEntity();

        helpMessageEntity.setCurrentToken(tokenManager.getActiveToken());
        helpMessageEntity.setRole("person");
        helpMessageEntity.setCurrentLon(latLong.getLongitude());
        helpMessageEntity.setCurrentLat(latLong.getLatitude());
        helpMessageEntity.setMessage(helpMessage);

        return serverApi.sendHelpMessage(helpMessageEntity);
    }


    @Override
    public Observable<ResponseBody> sendDestinationMessage(LatLongPair destination) {
        LatLongPair currentLatLong = getCurrentLatLong();
        String currentRole = getCurrentMode();

        DestinationEntity destinationEntity = new DestinationEntity();

        destinationEntity.setCurrentLat(currentLatLong.getLatitude());
        destinationEntity.setCurrentLon(currentLatLong.getLongitude());
        destinationEntity.setDestinationLat(destination.getLatitude());
        destinationEntity.setDestinationLon(destination.getLongitude());
        destinationEntity.setCurrentToken(tokenManager.getActiveToken());
        destinationEntity.setRole(currentRole);

        return serverApi.sendDestination(destinationEntity);
    }


    @Override
    public Observable<ResponseBody> sendUpdateTokenRequest() {
        TokenUpdateEntity tokenUpdateEntity = new TokenUpdateEntity();

        tokenUpdateEntity.setCurrentToken(tokenManager.getActiveToken());
        tokenUpdateEntity.setOldToken(tokenManager.getOldToken());

        return serverApi.tokenUpdateRequest(tokenUpdateEntity);
    }

    @Override
    public Observable<ResponseBody> sendChangeStatusRequest(String status) {
        StatusEntity statusEntity = new StatusEntity();
        statusEntity.setCurrentToken(tokenManager.getActiveToken());
        statusEntity.setRole(status);

        return serverApi.changeStatus(statusEntity);
    }

    private LatLongPair getCurrentLatLong() {
        double latitude = Double.valueOf(sharedPrefs.getString(context.getString(R.string.current_latitude), ""));
        double longitude = Double.valueOf(sharedPrefs.getString(context.getString(R.string.current_longitude), ""));

        return new LatLongPair(latitude, longitude);
    }

    private String getCurrentMode() {
        int currentMode = userDataManager.getUserMode();

        switch (currentMode) {
            case EndPointFragment.DRIVER_MODE:
                return context.getString(R.string.driver_mode);
            case EndPointFragment.AMBULANCE_MODE:
                return context.getString(R.string.ambulance_mode);
            default:
                return context.getString(R.string.driver_mode);
        }
    }
}
