package com.example.scame.savealifenotifier.domain.usecases;


import com.example.scame.savealifenotifier.data.entities.LatLongPair;
import com.example.scame.savealifenotifier.data.repository.IDirectionsDataManager;
import com.example.scame.savealifenotifier.domain.schedulers.ObserveOn;
import com.example.scame.savealifenotifier.domain.schedulers.SubscribeOn;
import com.example.scame.savealifenotifier.presentation.models.DirectionModel;

import rx.Observable;

public class ComputeDirectionUseCase extends UseCase<DirectionModel> {

    private IDirectionsDataManager dataManager;

    private LatLongPair origin;
    private LatLongPair destination;

    public ComputeDirectionUseCase(SubscribeOn subscribeOn, ObserveOn observeOn, IDirectionsDataManager dataManager) {
        super(subscribeOn, observeOn);
        this.dataManager = dataManager;
    }

    @Override
    protected Observable<DirectionModel> getUseCaseObservable() {
        return dataManager.getDirections(origin, destination);
    }

    public void setOrigin(LatLongPair origin) {
        this.origin = origin;
    }

    public void setDestination(LatLongPair destination) {
        this.destination = destination;
    }
}