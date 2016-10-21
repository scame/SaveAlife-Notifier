package com.example.scame.savealifenotifier.presentation.presenters;


import android.util.Log;

import com.example.scame.savealifenotifier.data.entities.LatLongPair;
import com.example.scame.savealifenotifier.domain.usecases.DefaultSubscriber;
import com.example.scame.savealifenotifier.domain.usecases.DeviceStatusUseCase;
import com.example.scame.savealifenotifier.domain.usecases.LocationUpdatesUseCase;
import com.example.scame.savealifenotifier.domain.usecases.MapboxDirectionUseCase;
import com.example.scame.savealifenotifier.domain.usecases.MapboxGeocodingUseCase;
import com.example.scame.savealifenotifier.domain.usecases.SetupDestinationUseCase;
import com.example.scame.savealifenotifier.domain.usecases.SetupUserModeUseCase;
import com.example.scame.savealifenotifier.presentation.models.AddressModel;
import com.example.scame.savealifenotifier.presentation.models.NewDirectionModel;
import com.mapbox.services.commons.models.Position;

import java.io.IOException;

import okhttp3.ResponseBody;

public class MapPresenterImp<T extends IMapPresenter.MapView> implements IMapPresenter<T> {

    private LocationUpdatesUseCase locationUpdatesUseCase;

    private DeviceStatusUseCase deviceStatusUseCase;

    private SetupDestinationUseCase destinationUseCase;

    private SetupUserModeUseCase userModeUseCase;

    private MapboxDirectionUseCase directionUseCase;

    private MapboxGeocodingUseCase geocodingUseCase;

    private T view;

    private LatLongPair destination;

    public MapPresenterImp(LocationUpdatesUseCase locationUpdatesUseCase,
                           DeviceStatusUseCase deviceStatusUseCase,
                           SetupDestinationUseCase destinationUseCase,
                           SetupUserModeUseCase userModeUseCase,
                           MapboxDirectionUseCase directionUseCase,
                           MapboxGeocodingUseCase geocodingUseCase) {
        this.locationUpdatesUseCase = locationUpdatesUseCase;
        this.deviceStatusUseCase = deviceStatusUseCase;
        this.destinationUseCase = destinationUseCase;
        this.userModeUseCase = userModeUseCase;
        this.directionUseCase = directionUseCase;
        this.geocodingUseCase = geocodingUseCase;
    }

    @Override
    public void geocodeToHumanReadableFormat(Position position) {
        geocodingUseCase.setPosition(position);
        geocodingUseCase.execute(new ReverseGeocodeSubscriber());
    }

    @Override
    public void computeDirection(LatLongPair origin, LatLongPair destination) {
        directionUseCase.setOrigin(origin);
        directionUseCase.setDestination(destination);
        directionUseCase.execute(new DirectionSubscriber());
    }

    @Override
    public void startLocationUpdates() {
        locationUpdatesUseCase.execute(new LocationUpdatesSubscriber());
    }

    @Override
    public void setupDestination(LatLongPair latLongPair) {
        this.destination = latLongPair;
        deviceStatusUseCase.execute(new DeviceStatusSubscriber());
    }

    @Override
    public void setupUserMode(int mode) {
        userModeUseCase.setUserMode(mode);
        userModeUseCase.execute(new DefaultSubscriber<>());
    }

    @Override
    public void changeDeviceStatus() {
        deviceStatusUseCase.execute(new DefaultSubscriber<>());
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
            if (view != null) {
                view.showHumanReadableAddress(latLng);
            }
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);

            Log.i("onxGeocodingError", e.getLocalizedMessage());
        }
    }

    private final class DirectionSubscriber extends DefaultSubscriber<NewDirectionModel> {

        @Override
        public void onNext(NewDirectionModel directionModel) {
            super.onNext(directionModel);

            if (view != null) {
                view.drawDirectionPolyline(directionModel.getPolyline());
            }
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);

            Log.i("onxDirectionError", e.getLocalizedMessage());
        }
    }

    private final class DestinationSubscriber extends DefaultSubscriber<ResponseBody> {

        @Override
        public void onNext(ResponseBody responseBody) {
            super.onNext(responseBody);

            try {
                Log.i("onxDestNext", responseBody.string());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onCompleted() {
            super.onCompleted();

            Log.i("onxDestCompleted", "completed");
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);

            Log.i("onxDestError", e.getLocalizedMessage());
        }
    }

    private final class DeviceStatusSubscriber extends DefaultSubscriber<ResponseBody> {

        @Override
        public void onCompleted() {
            super.onCompleted();

            destinationUseCase.setLatLongPair(destination);
            destinationUseCase.execute(new DestinationSubscriber());
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);

            Log.i("onxError", e.getLocalizedMessage());
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

            //Log.i("onxNext", latLongPair.getLatitude() + "," + latLongPair.getLongitude());
            if (view != null) {
                view.updateCurrentLocation(latLongPair);
            }
        }

        @Override
        public void onCompleted() {
            super.onCompleted();

            Log.i("onxCompleted", "true");
        }
    }
}
