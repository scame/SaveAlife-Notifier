package com.example.scame.savealifenotifier.domain.usecases;


import com.example.scame.savealifenotifier.data.repository.IMessagesDataManager;
import com.example.scame.savealifenotifier.domain.schedulers.ObserveOn;
import com.example.scame.savealifenotifier.domain.schedulers.SubscribeOn;

import okhttp3.ResponseBody;
import rx.Observable;

public class HelpMessageUseCase extends UseCase<ResponseBody> {

    private IMessagesDataManager messagesDataManager;

    private String message;

    public HelpMessageUseCase(SubscribeOn subscribeOn, ObserveOn observeOn, IMessagesDataManager messagesDataManager) {
        super(subscribeOn, observeOn);
        this.messagesDataManager = messagesDataManager;
    }

    @Override
    protected Observable<ResponseBody> getUseCaseObservable() {
        return messagesDataManager.sendHelpMeMessage(message);
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
