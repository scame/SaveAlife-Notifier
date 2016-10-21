package com.example.scame.savealifenotifier.presentation.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.scame.savealifenotifier.R;
import com.example.scame.savealifenotifier.presentation.di.components.ApplicationComponent;
import com.example.scame.savealifenotifier.presentation.models.DriversMessageModel;
import com.mapbox.mapboxsdk.annotations.PolylineOptions;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.SupportMapFragment;

import java.util.List;

public class DriversHelpMapActivity extends BaseActivity {

    private static final String MAPBOX_FRAG_TAG = "mapboxTag";

    private SupportMapFragment supportMapFragment;

    private List<LatLng> latLngList;

    private String message;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drivers_map_activity);

        parseRevisedRoute();
        initializeMapFragment();
    }

    private void initializeMapFragment() {
        if (getSupportFragmentManager().findFragmentByTag(MAPBOX_FRAG_TAG) == null) {
            supportMapFragment = SupportMapFragment.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.drivers_help_fl,
                    supportMapFragment, MAPBOX_FRAG_TAG).commit();
        } else {
            supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentByTag(MAPBOX_FRAG_TAG);
        }

        supportMapFragment.getMapAsync(this::onMapReady);
    }

    private void onMapReady(MapboxMap mapboxMap) {
        if (latLngList != null) {
            mapboxMap.addPolyline(new PolylineOptions().addAll(latLngList));
            mapboxMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngList.get(0), 13));
        }
    }

    private void parseRevisedRoute() {
        Intent i = getIntent();
        DriversMessageModel messageModel = i.getParcelableExtra(DriversHelpMapActivity.class.getCanonicalName());
        latLngList = messageModel.getPath();
        message = messageModel.getMessageBody();
    }


    @Override
    protected void inject(ApplicationComponent component) {

    }
}
