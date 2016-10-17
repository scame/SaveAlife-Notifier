package com.example.scame.savealifenotifier.data.repository;

import com.example.scame.savealifenotifier.PrivateValues;
import com.example.scame.savealifenotifier.data.api.GeocodingApi;
import com.example.scame.savealifenotifier.data.mappers.AddressModelMapper;
import com.example.scame.savealifenotifier.presentation.models.AddressModel;

import rx.Observable;

public class GeocodingDataManagerImp implements IGeocodingDataManager {

    private GeocodingApi geocodingApi;

    private AddressModelMapper addressModelMapper;

    public GeocodingDataManagerImp(AddressModelMapper addressModelMapper, GeocodingApi geocodingApi) {
        this.addressModelMapper = addressModelMapper;
        this.geocodingApi = geocodingApi;
    }

    @Override
    public Observable<AddressModel> getHumanReadableAddress(String latLng) {
        return geocodingApi.reverseGeocode(latLng, PrivateValues.GOOGLE_KEY)
                .map(addressModelMapper::convert);
    }
}
