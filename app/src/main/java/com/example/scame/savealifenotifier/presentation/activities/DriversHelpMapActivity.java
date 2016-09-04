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
import com.google.maps.android.geometry.Point;

import java.util.List;

public class DriversHelpMapActivity extends BaseActivity implements OnMapReadyCallback {

    private GoogleMap googleMap;

    private List<Point> pointList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drivers_map_activity);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map_fragment);
        mapFragment.getMapAsync(this);

        // TODO: add current location updates

        parseIntent();
    }

    private void parseIntent() {
        Intent i = getIntent();
        DriversMessageModel messageModel = i.getParcelableExtra(DriversHelpMapActivity.class.getCanonicalName());

        // TODO: draw a path based on messageModel's points
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap =  googleMap;
    }

    @Override
    protected void inject(ApplicationComponent component) {

    }
}
