package com.example.scame.savealifenotifier.domain.usecases;


import com.example.scame.savealifenotifier.data.entities.LatLongPair;
import com.example.scame.savealifenotifier.data.repository.IMessagesDataManager;
import com.example.scame.savealifenotifier.domain.schedulers.ObserveOn;
import com.example.scame.savealifenotifier.domain.schedulers.SubscribeOn;

import okhttp3.ResponseBody;
import rx.Observable;

public class SetupDestinationUseCase extends UseCase<ResponseBody> {

    private IMessagesDataManager messagesDataManager;

    private LatLongPair latLongPair;

    public SetupDestinationUseCase(SubscribeOn subscribeOn, ObserveOn observeOn, IMessagesDataManager messagesDataManager) {
        super(subscribeOn, observeOn);
        this.messagesDataManager = messagesDataManager;
    }

    @Override
    protected Observable<ResponseBody> getUseCaseObservable() {
        return messagesDataManager.sendDestinationMessage(latLongPair);
    }

    public void setLatLongPair(LatLongPair latLongPair) {
        this.latLongPair = latLongPair;
    }

    public LatLongPair getLatLongPair() {
        return latLongPair;
    }
}
