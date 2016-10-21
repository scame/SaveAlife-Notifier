package com.example.scame.savealifenotifier.presentation.di.modules;


import com.example.scame.savealifenotifier.data.repository.ILocationDataManager;
import com.example.scame.savealifenotifier.data.repository.IMapBoxDataManager;
import com.example.scame.savealifenotifier.data.repository.IMessagesDataManager;
import com.example.scame.savealifenotifier.data.repository.IUserDataManager;
import com.example.scame.savealifenotifier.domain.schedulers.ObserveOn;
import com.example.scame.savealifenotifier.domain.schedulers.SubscribeOn;
import com.example.scame.savealifenotifier.domain.usecases.DeviceStatusUseCase;
import com.example.scame.savealifenotifier.domain.usecases.LocationUpdatesUseCase;
import com.example.scame.savealifenotifier.domain.usecases.MapboxDirectionUseCase;
import com.example.scame.savealifenotifier.domain.usecases.MapboxGeocodingUseCase;
import com.example.scame.savealifenotifier.domain.usecases.SetupDestinationUseCase;
import com.example.scame.savealifenotifier.domain.usecases.SetupUserModeUseCase;
import com.example.scame.savealifenotifier.presentation.di.PerActivity;
import com.example.scame.savealifenotifier.presentation.presenters.IMapPresenter;
import com.example.scame.savealifenotifier.presentation.presenters.MapPresenterImp;

import dagger.Module;
import dagger.Provides;

@Module
public class MapboxModule {

    @Provides
    @PerActivity
    IMapPresenter<IMapPresenter.MapView> provideMapPresenter(LocationUpdatesUseCase locationUpdatesUseCase,
                                                             DeviceStatusUseCase deviceStatusUseCase,
                                                             SetupDestinationUseCase setupDestinationUseCase,
                                                             SetupUserModeUseCase setupUserModeUseCase,
                                                             MapboxDirectionUseCase mapboxDirectionUseCase,
                                                             MapboxGeocodingUseCase mapboxGeocodingUseCase) {
        return new MapPresenterImp<>(locationUpdatesUseCase, deviceStatusUseCase, setupDestinationUseCase,
                setupUserModeUseCase, mapboxDirectionUseCase, mapboxGeocodingUseCase);
    }

    @PerActivity
    @Provides
    MapboxDirectionUseCase provideDirectionUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                                   IMapBoxDataManager dataManager) {
        return new MapboxDirectionUseCase(subscribeOn, observeOn, dataManager);
    }

    @PerActivity
    @Provides
    MapboxGeocodingUseCase provideGeocodingUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                                   IMapBoxDataManager dataManager) {
        return new MapboxGeocodingUseCase(subscribeOn, observeOn, dataManager);
    }

    @PerActivity
    @Provides
    LocationUpdatesUseCase provideLocationUpdatesUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                                         ILocationDataManager dataManager) {

        return new LocationUpdatesUseCase(subscribeOn, observeOn, dataManager);
    }

    @PerActivity
    @Provides
    SetupDestinationUseCase provideSetupDestinationUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                                           IMessagesDataManager messagesDataManager) {

        return new SetupDestinationUseCase(subscribeOn, observeOn, messagesDataManager);
    }


    @PerActivity
    @Provides
    SetupUserModeUseCase provideUserModeUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                                IUserDataManager userDataManager) {

        return new SetupUserModeUseCase(subscribeOn, observeOn, userDataManager);
    }

    @PerActivity
    @Provides
    DeviceStatusUseCase provideDeviceStatusUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                                   IMessagesDataManager dataManager) {

        return new DeviceStatusUseCase(subscribeOn, observeOn, dataManager);
    }
}
