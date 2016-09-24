package com.example.scame.savealifenotifier.presentation.presenters;

import com.example.scame.savealifenotifier.data.entities.LatLongPair;
import com.google.android.gms.maps.model.PolylineOptions;

public interface IEndPointPresenter<T> extends Presenter<T> {

    interface EndPointView {

        void showHumanReadableAddress(String latLng);

        void drawDirectionPolyline(PolylineOptions polyline);

        void updateCurrentLocation(LatLongPair latLongPair);
    }

    void geocodeToHumanReadableFormat(String latLng);

    void computeDirection(LatLongPair origin, LatLongPair destination);

    void startLocationUpdates();

    void setupDestination(LatLongPair latLongPair);

    void setupUserMode(int mode);

    void changeDeviceStatus();
}