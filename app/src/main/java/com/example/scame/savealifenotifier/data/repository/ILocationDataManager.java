package com.example.scame.savealifenotifier.data.repository;

import com.example.scame.savealifenotifier.data.entities.LatLongPair;

import rx.Observable;

public interface ILocationDataManager {

    Observable<LatLongPair> startLocationUpdates();

    void stopLocationUpdates();

    void saveCurrentLocation(LatLongPair latLongPair);

    LatLongPair getCurrentLocation();
}
