package com.example.scame.savealifenotifier.presentation.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.scame.savealifenotifier.R;
import com.example.scame.savealifenotifier.presentation.di.components.ApplicationComponent;
import com.example.scame.savealifenotifier.presentation.models.DriversMessageModel;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

public class DriversHelpMapActivity extends BaseActivity implements OnMapReadyCallback {

    private GoogleMap googleMap;

    private List<LatLng> latLngList;

    private String message;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drivers_map_activity);

        parseRevisedRoute();

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map_fragment);
        mapFragment.getMapAsync(this);

    }

    private void parseRevisedRoute() {
        Intent i = getIntent();
        DriversMessageModel messageModel = i.getParcelableExtra(DriversHelpMapActivity.class.getCanonicalName());
        latLngList = messageModel.getPath();
        message = messageModel.getMessageBody();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap =  googleMap;

        if (latLngList != null) {
            googleMap.addPolyline(new PolylineOptions().addAll(latLngList));
        }
    }

    @Override
    protected void inject(ApplicationComponent component) {

    }
}
