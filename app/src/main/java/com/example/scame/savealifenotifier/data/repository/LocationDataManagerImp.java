package com.example.scame.savealifenotifier.data.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.scame.savealifenotifier.R;
import com.example.scame.savealifenotifier.SaveAlifeApp;
import com.example.scame.savealifenotifier.data.entities.LatLongPair;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.net.ConnectException;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

public class LocationDataManagerImp implements ILocationDataManager, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private SharedPreferences sharedPrefs;

    private Context context;

    private GoogleApiClient googleApiClient;

    private LocationRequest locationRequest;

    private Subscriber<? super LatLongPair> subscriber;

    private Subscription subscription;

    private long UPDATE_INTERVAL = 10 * 1000;
    private long FASTEST_INTERVAL = 2000;

    public LocationDataManagerImp(SharedPreferences sharedPrefs, Context context) {
        this.sharedPrefs = sharedPrefs;
        this.context = context;
    }

    @SuppressWarnings({"MissingPermission"})
    public Observable<LatLongPair> startLocationUpdates() {

        return Observable.create(subscriberArg -> {
            subscriber = subscriberArg;
            buildGoogleApiClient();
            googleApiClient.connect();

            subscriberArg.add(subscription = Subscriptions.create(() -> {
                if (googleApiClient.isConnected() || googleApiClient.isConnecting()) {
                    LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
                    googleApiClient.disconnect();
                }
            }));
        });
    }

    public void stopLocationUpdates() {
        if (!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    private void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(SaveAlifeApp.getAppComponent().getApp())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    @Override
    public void onConnectionSuspended(int i) {
        subscriber.onError(new ConnectException("connectionSuspended"));
    }

    @Override
    @SuppressWarnings({"MissingPermission"})
    public void onConnected(@Nullable Bundle bundle) {
        Location currentLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

        if (currentLocation != null) {
            subscriber.onNext(new LatLongPair(currentLocation.getLatitude(), currentLocation.getLongitude()));
        }

        fireLocationUpdates();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        subscriber.onError(new ConnectException("failedToConnect"));
    }

    @Override
    public void onLocationChanged(Location location) {
        subscriber.onNext(new LatLongPair(location.getLatitude(), location.getLongitude()));
    }

    @SuppressWarnings({"MissingPermission"})
    private void fireLocationUpdates() {
        locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setFastestInterval(FASTEST_INTERVAL)
                .setInterval(UPDATE_INTERVAL);

        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
    }

    @Override
    public void saveCurrentLocation(LatLongPair latLongPair) {
        String latitude = String.valueOf(latLongPair.getLatitude());
        String longitude = String.valueOf(latLongPair.getLongitude());

        sharedPrefs.edit().putString(context.getString(R.string.current_latitude), latitude).apply();
        sharedPrefs.edit().putString(context.getString(R.string.current_longitude), longitude).apply();
    }

    @Override
    public LatLongPair getCurrentLocation() {
        double latitude = Double.valueOf(sharedPrefs.getString(context.getString(R.string.current_latitude), ""));
        double longitude = Double.valueOf(sharedPrefs.getString(context.getString(R.string.current_longitude), ""));

        return new LatLongPair(latitude, longitude);
    }
}
