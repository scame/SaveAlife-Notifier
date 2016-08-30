package com.example.scame.savealifenotifier.data.repository;

import com.example.scame.savealifenotifier.data.entities.GeocodingEntity;

import rx.Observable;

public interface IGeocodingDataManager {

    Observable<GeocodingEntity> getHumanReadableAddress(String latLng);
}
