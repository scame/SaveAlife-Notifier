package com.example.scame.savealifenotifier.presentation.presenters;


import com.example.scame.savealifenotifier.data.entities.LatLongPair;
import com.mapbox.mapboxsdk.annotations.PolylineOptions;
import com.mapbox.services.commons.models.Position;

public interface IMapPresenter<T> extends Presenter<T> {

    interface MapView {

        void showHumanReadableAddress(String latLng);

        void drawDirectionPolyline(PolylineOptions polylineOptions);

        void updateCurrentLocation(LatLongPair latLongPair);
    }

    void geocodeToHumanReadableFormat(Position position);

    void computeDirection(LatLongPair origin, LatLongPair destination);

    void startLocationUpdates();

    void setupDestination(LatLongPair latLongPair);

    void setupUserMode(int mode);

    void changeDeviceStatus();
}
