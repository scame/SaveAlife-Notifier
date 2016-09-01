package com.example.scame.savealifenotifier.presentation.presenters;

import android.util.Log;

import com.example.scame.savealifenotifier.data.entities.LatLongPair;
import com.example.scame.savealifenotifier.domain.usecases.ComputeDirectionUseCase;
import com.example.scame.savealifenotifier.domain.usecases.DefaultSubscriber;
import com.example.scame.savealifenotifier.domain.usecases.LocationUpdatesUseCase;
import com.example.scame.savealifenotifier.domain.usecases.ReverseGeocodeUseCase;
import com.example.scame.savealifenotifier.domain.usecases.SetupDestinationUseCase;
import com.example.scame.savealifenotifier.domain.usecases.StopDrivingModeUseCase;
import com.example.scame.savealifenotifier.presentation.models.AddressModel;
import com.example.scame.savealifenotifier.presentation.models.DirectionModel;

import java.io.IOException;

import okhttp3.ResponseBody;

public class EndPointPresenterImp<T extends IEndPointPresenter.EndPointView>
        implements IEndPointPresenter<T> {

    private ReverseGeocodeUseCase reverseGeocodeUseCase;

    private ComputeDirectionUseCase computeDirectionUseCase;

    private LocationUpdatesUseCase locationUpdatesUseCase;

    private SetupDestinationUseCase destinationUseCase;

    private StopDrivingModeUseCase drivingModeUseCase;

    private T view;

    public EndPointPresenterImp(ReverseGeocodeUseCase reverseGeocodeUseCase,
                                ComputeDirectionUseCase computeDirectionUseCase,
                                LocationUpdatesUseCase locationUpdatesUseCase,
                                SetupDestinationUseCase destinationUseCase,
                                StopDrivingModeUseCase drivingModeUseCase) {

        this.reverseGeocodeUseCase = reverseGeocodeUseCase;
        this.computeDirectionUseCase = computeDirectionUseCase;
        this.locationUpdatesUseCase = locationUpdatesUseCase;
        this.destinationUseCase = destinationUseCase;
        this.drivingModeUseCase = drivingModeUseCase;
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
    public void setupDestination(LatLongPair latLongPair) {
        destinationUseCase.setLatLongPair(latLongPair);
        destinationUseCase.execute(new DestinationSubscriber());
    }

    @Override
    public void stopDrivingMode() {
        drivingModeUseCase.execute(new DrivingModeSubscriber());
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

    private final class DrivingModeSubscriber extends DefaultSubscriber<ResponseBody> {

        @Override
        public void onCompleted() {
            super.onCompleted();

            Log.i("onCompleted", "completed");
        }

        @Override
        public void onNext(ResponseBody responseBody) {
            super.onNext(responseBody);

            try {
                Log.i("onNext", responseBody.string());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);

            Log.i("onError", e.getLocalizedMessage());
        }
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