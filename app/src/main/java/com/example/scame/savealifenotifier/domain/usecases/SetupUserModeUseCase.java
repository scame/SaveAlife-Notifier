package com.example.scame.savealifenotifier.domain.usecases;


import com.example.scame.savealifenotifier.data.repository.IUserDataManager;
import com.example.scame.savealifenotifier.domain.schedulers.ObserveOn;
import com.example.scame.savealifenotifier.domain.schedulers.SubscribeOn;

import rx.Observable;

public class SetupUserModeUseCase extends UseCase<Void> {

    private IUserDataManager userDataManager;

    private int userMode;

    public SetupUserModeUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                IUserDataManager userDataManager) {
        super(subscribeOn, observeOn);

        this.userDataManager = userDataManager;
    }

    @Override
    protected Observable<Void> getUseCaseObservable() {
        userDataManager.setupUserMode(userMode);

        return Observable.empty();
    }

    public void setUserMode(int userMode) {
        this.userMode = userMode;
    }
}
