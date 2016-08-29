package com.example.scame.savealifenotifier.presentation.di.modules;

import com.example.scame.savealifenotifier.data.repository.IMessagesDataManager;
import com.example.scame.savealifenotifier.domain.schedulers.ObserveOn;
import com.example.scame.savealifenotifier.domain.schedulers.SubscribeOn;
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
    IHelpMePresenter<IHelpMePresenter.HelpMeView> provideHelpMePresenter(HelpMessageUseCase useCase) {
        return new HelpMePresenterImp<>(useCase);
    }


    @PerActivity
    @Provides
    HelpMessageUseCase provideHelpMessageUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                                 IMessagesDataManager dataManager) {
        return new HelpMessageUseCase(subscribeOn, observeOn, dataManager);
    }
}
