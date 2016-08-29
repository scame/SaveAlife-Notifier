package com.example.scame.savealifenotifier.domain.usecases;


import com.example.scame.savealifenotifier.data.repository.IMessagesDataManager;
import com.example.scame.savealifenotifier.domain.schedulers.ObserveOn;
import com.example.scame.savealifenotifier.domain.schedulers.SubscribeOn;

import okhttp3.ResponseBody;
import rx.Observable;

public class HelpMessageUseCase extends UseCase<ResponseBody> {

    private IMessagesDataManager dataManager;

    private String message;

    public HelpMessageUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                              IMessagesDataManager dataManager) {

        super(subscribeOn, observeOn);
        this.dataManager = dataManager;
    }

    @Override
    protected Observable<ResponseBody> getUseCaseObservable() {
        return dataManager.sendHelpMeMessage(message);
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
