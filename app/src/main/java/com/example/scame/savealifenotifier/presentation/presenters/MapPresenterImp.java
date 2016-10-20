package com.example.scame.savealifenotifier.presentation.presenters;


import com.example.scame.savealifenotifier.data.entities.LatLongPair;

public class MapPresenterImp<T extends IMapPresenter.MapView> implements IMapPresenter<T> {

    @Override
    public void geocodeToHumanReadableFormat(String latLng) {

    }

    @Override
    public void computeDirection(LatLongPair origin, LatLongPair destination) {

    }

    @Override
    public void startLocationUpdates() {

    }

    @Override
    public void setupDestination(LatLongPair latLongPair) {

    }

    @Override
    public void setupUserMode(int mode) {

    }

    @Override
    public void changeDeviceStatus() {

    }

    @Override
    public void setView(T view) {

    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }
}
