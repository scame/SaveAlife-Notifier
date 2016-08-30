package com.example.scame.savealifenotifier.data.repository;

import com.example.scame.savealifenotifier.PrivateValues;
import com.example.scame.savealifenotifier.SaveAlifeApp;
import com.example.scame.savealifenotifier.data.api.DirectionsApi;
import com.example.scame.savealifenotifier.data.entities.DirectionEntity;
import com.example.scame.savealifenotifier.data.entities.LatLongPair;

import retrofit2.Retrofit;
import rx.Observable;

public class DirectionsDataManagerImp implements IDirectionsDataManager {


    @Override
    public Observable<DirectionEntity> getDirections(LatLongPair origin, LatLongPair destination) {
        Retrofit retrofit = SaveAlifeApp.getAppComponent().getRetrofit();
        DirectionsApi directionsApi = retrofit.create(DirectionsApi.class);

        return directionsApi.computeDirections(origin.getLatitude() + "," + origin.getLongitude(),
                destination.getLatitude() + "," + destination.getLongitude(),
                PrivateValues.GOOGLE_KEY);
    }
}
