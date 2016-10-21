package com.example.scame.savealifenotifier.domain.usecases;


import com.example.scame.savealifenotifier.data.entities.LatLongPair;
import com.example.scame.savealifenotifier.data.repository.IMapBoxDataManager;
import com.example.scame.savealifenotifier.domain.schedulers.ObserveOn;
import com.example.scame.savealifenotifier.domain.schedulers.SubscribeOn;
import com.example.scame.savealifenotifier.presentation.models.NewDirectionModel;

import rx.Observable;

public class MapboxDirectionUseCase extends UseCase<NewDirectionModel> {

    private IMapBoxDataManager mapBoxDataManager;

    private LatLongPair origin;

    private LatLongPair dest;

    public MapboxDirectionUseCase(SubscribeOn subscribeOn, ObserveOn observeOn, IMapBoxDataManager mapBoxDataManager) {
        super(subscribeOn, observeOn);
        this.mapBoxDataManager = mapBoxDataManager;
    }

    @Override
    protected Observable<NewDirectionModel> getUseCaseObservable() {
        return mapBoxDataManager.getDirection(origin, dest);
    }

    public void setOrigin(LatLongPair origin) {
        this.origin = origin;
    }

    public void setDestination(LatLongPair dest) {
        this.dest = dest;
    }
}
