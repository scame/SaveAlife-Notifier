package com.example.scame.savealifenotifier.domain.usecases;

import com.example.scame.savealifenotifier.data.entities.LatLongPair;
import com.example.scame.savealifenotifier.data.repository.ILocationDataManager;
import com.example.scame.savealifenotifier.domain.schedulers.ObserveOn;
import com.example.scame.savealifenotifier.domain.schedulers.SubscribeOn;

import rx.Observable;

public class LocationUpdatesUseCase extends UseCase<LatLongPair> {

    private ILocationDataManager dataManager;

    public LocationUpdatesUseCase(SubscribeOn subscribeOn, ObserveOn observeOn, ILocationDataManager dataManager) {
        super(subscribeOn, observeOn);
        this.dataManager = dataManager;
    }

    @Override
    protected Observable<LatLongPair> getUseCaseObservable() {
        return dataManager.startLocationUpdates();
    }

    @Override
    public void unsubscribe() {
        super.unsubscribe();

        dataManager.stopLocationUpdates();
    }
}
