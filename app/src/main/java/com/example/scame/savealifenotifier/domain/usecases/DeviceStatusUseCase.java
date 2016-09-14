package com.example.scame.savealifenotifier.domain.usecases;


import com.example.scame.savealifenotifier.data.repository.IMessagesDataManager;
import com.example.scame.savealifenotifier.domain.schedulers.ObserveOn;
import com.example.scame.savealifenotifier.domain.schedulers.SubscribeOn;

import okhttp3.ResponseBody;
import rx.Observable;

public class DeviceStatusUseCase extends UseCase<ResponseBody> {

    private IMessagesDataManager dataManager;

    private String deviceStatus;

    public DeviceStatusUseCase(SubscribeOn subscribeOn, ObserveOn observeOn, IMessagesDataManager dataManager) {
        super(subscribeOn, observeOn);

        this.dataManager = dataManager;
    }

    @Override
    protected Observable<ResponseBody> getUseCaseObservable() {
        return dataManager.sendChangeStatusRequest(deviceStatus);
    }

    public void setDeviceStatus(String deviceStatus) {
        this.deviceStatus = deviceStatus;
    }
}
