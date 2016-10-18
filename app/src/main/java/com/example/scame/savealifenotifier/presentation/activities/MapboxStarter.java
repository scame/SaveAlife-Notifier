package com.example.scame.savealifenotifier.presentation.activities;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.scame.savealifenotifier.PrivateValues;
import com.example.scame.savealifenotifier.R;
import com.mapbox.mapboxsdk.MapboxAccountManager;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MapboxStarter extends AppCompatActivity {

    @BindView(R.id.mapbox_mapview) MapView mapboxView;

    private MapboxMap mapboxMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MapboxAccountManager.start(this, PrivateValues.MAPBOX_KEY);
        setContentView(R.layout.mapbox_starter);

        ButterKnife.bind(this);
        mapboxView.onCreate(savedInstanceState);
        mapboxView.getMapAsync(mapboxMap -> this.mapboxMap = mapboxMap);
    }


    @Override
    public void onResume() {
        super.onResume();
        mapboxView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapboxView.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapboxView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapboxView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapboxView.onDestroy();
    }
}
