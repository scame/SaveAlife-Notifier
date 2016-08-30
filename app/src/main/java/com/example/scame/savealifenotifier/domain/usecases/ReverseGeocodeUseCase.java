package com.example.scame.savealifenotifier.domain.usecases;

import com.example.scame.savealifenotifier.data.mappers.AddressModelMapper;
import com.example.scame.savealifenotifier.data.repository.IGeocodingDataManager;
import com.example.scame.savealifenotifier.domain.schedulers.ObserveOn;
import com.example.scame.savealifenotifier.domain.schedulers.SubscribeOn;
import com.example.scame.savealifenotifier.presentation.models.AddressModel;

import rx.Observable;

public class ReverseGeocodeUseCase extends UseCase<AddressModel> {

    private IGeocodingDataManager dataManager;
    private AddressModelMapper mapper;

    private String latLng;

    public ReverseGeocodeUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                 IGeocodingDataManager dataManager) {
        super(subscribeOn, observeOn);

        this.dataManager = dataManager;
        mapper = new AddressModelMapper();
    }

    @Override
    protected Observable<AddressModel> getUseCaseObservable() {

        return dataManager.getHumanReadableAddress(latLng)
                .map(mapper::convert);
    }

    public void setLatLng(String latLng) {
        this.latLng = latLng;
    }
}