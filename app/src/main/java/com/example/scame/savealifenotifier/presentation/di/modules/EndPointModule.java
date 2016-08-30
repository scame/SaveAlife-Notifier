package com.example.scame.savealifenotifier.presentation.di.modules;

import com.example.scame.savealifenotifier.data.repository.IDirectionsDataManager;
import com.example.scame.savealifenotifier.data.repository.IGeocodingDataManager;
import com.example.scame.savealifenotifier.data.repository.ILocationDataManager;
import com.example.scame.savealifenotifier.domain.schedulers.ObserveOn;
import com.example.scame.savealifenotifier.domain.schedulers.SubscribeOn;
import com.example.scame.savealifenotifier.domain.usecases.ComputeDirectionUseCase;
import com.example.scame.savealifenotifier.domain.usecases.LocationUpdatesUseCase;
import com.example.scame.savealifenotifier.domain.usecases.ReverseGeocodeUseCase;
import com.example.scame.savealifenotifier.presentation.di.PerActivity;
import com.example.scame.savealifenotifier.presentation.presenters.EndPointPresenterImp;
import com.example.scame.savealifenotifier.presentation.presenters.IEndPointPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class EndPointModule {

    @PerActivity
    @Provides
    IEndPointPresenter<IEndPointPresenter.EndPointView> providePointLocationPresenter(ReverseGeocodeUseCase reverseGeocodeUseCase,
                                                                              ComputeDirectionUseCase computeDirectionUseCase,
                                                                              LocationUpdatesUseCase locationUpdatesUseCase) {
        return new EndPointPresenterImp<>(reverseGeocodeUseCase, computeDirectionUseCase, locationUpdatesUseCase);
    }

    @PerActivity
    @Provides
    ReverseGeocodeUseCase provideReverseGeocodeUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                                       IGeocodingDataManager dataManager) {

        return new ReverseGeocodeUseCase(subscribeOn, observeOn, dataManager);
    }

    @PerActivity
    @Provides
    ComputeDirectionUseCase computeDirectionUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                                    IDirectionsDataManager dataManager) {

        return new ComputeDirectionUseCase(subscribeOn, observeOn, dataManager);
    }

    @PerActivity
    @Provides
    LocationUpdatesUseCase locationUpdatesUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                                  ILocationDataManager dataManager) {

        return new LocationUpdatesUseCase(subscribeOn, observeOn, dataManager);
    }
}
