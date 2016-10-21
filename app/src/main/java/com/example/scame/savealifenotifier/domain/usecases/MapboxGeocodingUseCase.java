package com.example.scame.savealifenotifier.domain.usecases;


import com.example.scame.savealifenotifier.data.repository.IMapBoxDataManager;
import com.example.scame.savealifenotifier.domain.schedulers.ObserveOn;
import com.example.scame.savealifenotifier.domain.schedulers.SubscribeOn;
import com.example.scame.savealifenotifier.presentation.models.AddressModel;
import com.mapbox.services.commons.models.Position;

import rx.Observable;

public class MapboxGeocodingUseCase extends UseCase<AddressModel> {

    private IMapBoxDataManager dataManager;

    private Position position;

    public MapboxGeocodingUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                  IMapBoxDataManager dataManager) {
        super(subscribeOn, observeOn);
        this.dataManager = dataManager;
    }

    @Override
    protected Observable<AddressModel> getUseCaseObservable() {
        return dataManager.getHumanReadableAddress(position);
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
