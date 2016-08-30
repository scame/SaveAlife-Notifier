package com.example.scame.savealifenotifier.data.repository;

import com.example.scame.savealifenotifier.PrivateValues;
import com.example.scame.savealifenotifier.SaveAlifeApp;
import com.example.scame.savealifenotifier.data.api.GeocodingApi;
import com.example.scame.savealifenotifier.data.entities.GeocodingEntity;

import retrofit2.Retrofit;
import rx.Observable;

public class GeocodingDataManagerImp implements IGeocodingDataManager {

    @Override
    public Observable<GeocodingEntity> getHumanReadableAddress(String latLng) {
        Retrofit retrofit = SaveAlifeApp.getAppComponent().getRetrofit();
        GeocodingApi geocodingApi = retrofit.create(GeocodingApi.class);

        return geocodingApi.reverseGeocode(latLng, PrivateValues.GOOGLE_KEY);
    }
}
