package com.example.scame.savealifenotifier.data.repository;


import android.content.Context;

import com.example.scame.savealifenotifier.R;
import com.example.scame.savealifenotifier.SaveAlifeApp;
import com.example.scame.savealifenotifier.data.api.ServerApi;
import com.example.scame.savealifenotifier.data.entities.DestinationEntity;
import com.example.scame.savealifenotifier.data.entities.HelpMessageEntity;
import com.example.scame.savealifenotifier.data.entities.LatLongPair;
import com.example.scame.savealifenotifier.data.entities.LocationMessageEntity;
import com.example.scame.savealifenotifier.data.entities.RegistrationEntity;
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

    private IFirebaseTokenManager tokenManager;

    private IUserDataManager userDataManager;

    private ILocationDataManager locationDataManager;

    public MessagesDataManagerImp() {
        retrofit = SaveAlifeApp.getAppComponent().getRetrofit();
        serverApi = retrofit.create(ServerApi.class);
        tokenManager = new FirebaseTokenManagerImp();
        userDataManager = new UserDataManagerImp();
        locationDataManager = new LocationDataManagerImp();

        context = SaveAlifeApp.getAppComponent().getApp();
    }

    @Override
    public Observable<ResponseBody> sendRegistrationRequest() {
        RegistrationEntity registrationEntity = new RegistrationEntity();
        registrationEntity.setCurrentToken(tokenManager.getActiveToken());

        return serverApi.sendRegistrationRequest(registrationEntity, context.getString(R.string.non_driver_mode));
    }

    @Override
    public Observable<ResponseBody> sendLocationMessage() {
        LatLongPair latLong = locationDataManager.getCurrentLocation();

        LocationMessageEntity locationEntity = new LocationMessageEntity();

        locationEntity.setCurrentToken(tokenManager.getActiveToken());
        locationEntity.setCurrentLat(latLong.getLatitude());
        locationEntity.setCurrentLon(latLong.getLongitude());

        if (getCurrentMode().equals(context.getString(R.string.ambulance_mode))) {
            locationEntity.setEnable(true);
        }

        return serverApi.sendLocationToServer(locationEntity, getCurrentMode());
    }

    @Override
    public Observable<ResponseBody> sendHelpMeMessage(String helpMessage) {
        LatLongPair latLong = locationDataManager.getCurrentLocation();

        HelpMessageEntity helpMessageEntity = new HelpMessageEntity();

        helpMessageEntity.setCurrentToken(tokenManager.getActiveToken());
        helpMessageEntity.setCurrentLon(latLong.getLongitude());
        helpMessageEntity.setCurrentLat(latLong.getLatitude());
        helpMessageEntity.setMessage(helpMessage);

        return serverApi.sendHelpMessage(helpMessageEntity, context.getString(R.string.non_driver_mode));
    }


    @Override
    public Observable<ResponseBody> sendDestinationMessage(LatLongPair destination) {
        LatLongPair currentLatLong = locationDataManager.getCurrentLocation();

        DestinationEntity destinationEntity = new DestinationEntity();

        destinationEntity.setCurrentLat(currentLatLong.getLatitude());
        destinationEntity.setCurrentLon(currentLatLong.getLongitude());
        destinationEntity.setDestinationLat(destination.getLatitude());
        destinationEntity.setDestinationLon(destination.getLongitude());
        destinationEntity.setCurrentToken(tokenManager.getActiveToken());


        return serverApi.sendDestination(destinationEntity, getCurrentMode());
    }


    @Override
    public Observable<ResponseBody> sendUpdateTokenRequest() {
        IFirebaseTokenManager tokenManager = new FirebaseTokenManagerImp();

        TokenUpdateEntity tokenUpdateEntity = new TokenUpdateEntity();
        tokenUpdateEntity.setOldToken(tokenManager.getOldToken());
        tokenUpdateEntity.setCurrentToken(tokenManager.getActiveToken());

        return serverApi.tokenUpdateRequest(tokenUpdateEntity);
    }

    @Override
    public Observable<ResponseBody> sendChangeStatusRequest() {
        StatusEntity statusEntity = new StatusEntity();

        statusEntity.setCurrentToken(tokenManager.getActiveToken());
        statusEntity.setRole(getCurrentMode());

        return serverApi.changeStatus(statusEntity);
    }

    private String getCurrentMode() {
        int currentMode = userDataManager.getUserMode();

        switch (currentMode) {
            case EndPointFragment.DRIVER_MODE:
                return context.getString(R.string.driver_mode);
            case EndPointFragment.AMBULANCE_MODE:
                return context.getString(R.string.ambulance_mode);
            case EndPointFragment.NON_DRIVER_MODE:
                return context.getString(R.string.non_driver_mode);
            default:
                return context.getString(R.string.non_driver_mode);
        }
    }
}
