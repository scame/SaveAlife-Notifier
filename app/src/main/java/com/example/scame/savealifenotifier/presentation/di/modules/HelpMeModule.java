package com.example.scame.savealifenotifier.presentation.di.modules;

import com.example.scame.savealifenotifier.data.repository.IMessagesDataManager;
import com.example.scame.savealifenotifier.domain.schedulers.ObserveOn;
import com.example.scame.savealifenotifier.domain.schedulers.SubscribeOn;
import com.example.scame.savealifenotifier.domain.usecases.DeviceStatusUseCase;
import com.example.scame.savealifenotifier.domain.usecases.HelpMessageUseCase;
import com.example.scame.savealifenotifier.presentation.di.PerActivity;
import com.example.scame.savealifenotifier.presentation.presenters.HelpMePresenterImp;
import com.example.scame.savealifenotifier.presentation.presenters.IHelpMePresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class HelpMeModule {

    @PerActivity
    @Provides
    IHelpMePresenter<IHelpMePresenter.HelpMeView> provideHelpMePresenter(HelpMessageUseCase helpUseCase,
                                                                         DeviceStatusUseCase statusUseCase) {
        return new HelpMePresenterImp<>(helpUseCase, statusUseCase);
    }


    @PerActivity
    @Provides
    HelpMessageUseCase provideHelpMessageUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                                 IMessagesDataManager dataManager) {
        return new HelpMessageUseCase(subscribeOn, observeOn, dataManager);
    }

    @PerActivity
    @Provides
    DeviceStatusUseCase provideDeviceStatusUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                                   IMessagesDataManager messagesDataManager) {

        return new DeviceStatusUseCase(subscribeOn, observeOn, messagesDataManager);
    }
}
