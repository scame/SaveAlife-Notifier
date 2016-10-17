package com.example.scame.savealifenotifier.data.repository;

import com.example.scame.savealifenotifier.data.entities.LatLongPair;
import com.example.scame.savealifenotifier.presentation.models.DirectionModel;

import rx.Observable;

public interface IDirectionsDataManager {

    Observable<DirectionModel> getDirections(LatLongPair origin, LatLongPair destination);
}