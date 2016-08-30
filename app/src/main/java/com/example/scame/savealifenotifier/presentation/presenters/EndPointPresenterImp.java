package com.example.scame.savealifenotifier.presentation.presenters;

import android.util.Log;

import com.example.scame.savealifenotifier.data.entities.LatLongPair;
import com.example.scame.savealifenotifier.domain.usecases.ComputeDirectionUseCase;
import com.example.scame.savealifenotifier.domain.usecases.DefaultSubscriber;
import com.example.scame.savealifenotifier.domain.usecases.LocationUpdatesUseCase;
import com.example.scame.savealifenotifier.domain.usecases.ReverseGeocodeUseCase;
import com.example.scame.savealifenotifier.presentation.models.AddressModel;
import com.example.scame.savealifenotifier.presentation.models.DirectionModel;

public class EndPointPresenterImp<T extends IEndPointPresenter.EndPointView>
        implements IEndPointPresenter<T> {

    private ReverseGeocodeUseCase reverseGeocodeUseCase;

    private ComputeDirectionUseCase computeDirectionUseCase;

    private LocationUpdatesUseCase locationUpdatesUseCase;

    private T view;

    public EndPointPresenterImp(ReverseGeocodeUseCase reverseGeocodeUseCase,
                                     ComputeDirectionUseCase computeDirectionUseCase,
                                     LocationUpdatesUseCase locationUpdatesUseCase) {

        this.reverseGeocodeUseCase = reverseGeocodeUseCase;
        this.computeDirectionUseCase = computeDirectionUseCase;
        this.locationUpdatesUseCase = locationUpdatesUseCase;
    }

    @Override
    public void geocodeToHumanReadableFormat(String latLng) {
        reverseGeocodeUseCase.setLatLng(latLng);
        reverseGeocodeUseCase.execute(new ReverseGeocodeSubscriber());
    }

    @Override
    public void computeDirection(LatLongPair origin, LatLongPair destination) {
        computeDirectionUseCase.setDestination(destination);
        computeDirectionUseCase.setOrigin(origin);

        computeDirectionUseCase.execute(new DirectionSubscriber());
    }

    @Override
    public void startLocationUpdates() {
        locationUpdatesUseCase.execute(new LocationUpdatesSubscriber());
    }

    @Override
    public void setView(T view) {
        this.view = view;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {
        locationUpdatesUseCase.unsubscribe();
    }

    @Override
    public void destroy() {

    }

    private final class ReverseGeocodeSubscriber extends DefaultSubscriber<AddressModel> {

        @Override
        public void onNext(AddressModel addressModel) {
            super.onNext(addressModel);

            String latLng = addressModel.getFormattedAddress();
            view.showHumanReadableAddress(latLng);
        }
    }

    private final class DirectionSubscriber extends DefaultSubscriber<DirectionModel> {

        @Override
        public void onNext(DirectionModel directionModel) {
            super.onNext(directionModel);

            view.drawDirectionPolyline(directionModel.getPolyline());
        }
    }

    private final class LocationUpdatesSubscriber extends DefaultSubscriber<LatLongPair> {

        @Override
        public void onError(Throwable e) {
            super.onError(e);

            Log.i("onxError", e.getLocalizedMessage());
        }

        @Override
        public void onNext(LatLongPair latLongPair) {
            super.onNext(latLongPair);

            Log.i("onxNext", latLongPair.getLatitude() + "," + latLongPair.getLongitude());
            view.updateCurrentLocation(latLongPair);
        }
    }
}