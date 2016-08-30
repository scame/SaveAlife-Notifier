package com.example.scame.savealifenotifier.data.repository;

import com.example.scame.savealifenotifier.data.entities.DirectionEntity;
import com.example.scame.savealifenotifier.data.entities.LatLongPair;

import rx.Observable;

public interface IDirectionsDataManager {

    Observable<DirectionEntity> getDirections(LatLongPair origin, LatLongPair destination);
}