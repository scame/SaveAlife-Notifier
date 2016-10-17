package com.example.scame.savealifenotifier.data.repository;

import com.example.scame.savealifenotifier.PrivateValues;
import com.example.scame.savealifenotifier.data.api.DirectionsApi;
import com.example.scame.savealifenotifier.data.entities.LatLongPair;
import com.example.scame.savealifenotifier.data.mappers.DirectionModelMapper;
import com.example.scame.savealifenotifier.presentation.models.DirectionModel;

import rx.Observable;

public class DirectionsDataManagerImp implements IDirectionsDataManager {

    private DirectionsApi directionsApi;

    private DirectionModelMapper directionModelMapper;

    public DirectionsDataManagerImp(DirectionsApi directionsApi, DirectionModelMapper directionModelMapper) {
        this.directionModelMapper = directionModelMapper;
        this.directionsApi = directionsApi;
    }

    @Override
    public Observable<DirectionModel> getDirections(LatLongPair origin, LatLongPair destination) {
        return directionsApi.computeDirections(origin.getLatitude() + "," + origin.getLongitude(),
                destination.getLatitude() + "," + destination.getLongitude(), PrivateValues.GOOGLE_KEY)
                .map(directionModelMapper::convert);
    }
}
