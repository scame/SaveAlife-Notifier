package com.example.scame.savealifenotifier.domain.usecases;

import com.example.scame.savealifenotifier.data.repository.IMessagesDataManager;
import com.example.scame.savealifenotifier.domain.schedulers.ObserveOn;
import com.example.scame.savealifenotifier.domain.schedulers.SubscribeOn;

import okhttp3.ResponseBody;
import rx.Observable;

public class StopDrivingModeUseCase extends UseCase<ResponseBody> {

    private static final String STATUS = "person";

    private IMessagesDataManager messagesDataManager;

    public StopDrivingModeUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                  IMessagesDataManager messagesDataManager) {

        super(subscribeOn, observeOn);
        this.messagesDataManager = messagesDataManager;
    }

    @Override
    protected Observable<ResponseBody> getUseCaseObservable() {
        return messagesDataManager.sendChangeStatusRequest(STATUS);
    }
}
