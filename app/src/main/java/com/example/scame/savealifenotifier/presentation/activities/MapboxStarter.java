package com.example.scame.savealifenotifier.presentation.activities;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.scame.savealifenotifier.PrivateValues;
import com.example.scame.savealifenotifier.R;
import com.example.scame.savealifenotifier.presentation.di.components.ApplicationComponent;
import com.example.scame.savealifenotifier.presentation.fragments.MapFragment;
import com.mapbox.mapboxsdk.MapboxAccountManager;

public class MapboxStarter extends BaseActivity {

    private static final String MAPBOX_FRAGMENT_TAG = "mapboxFragment";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MapboxAccountManager.start(this, PrivateValues.MAPBOX_KEY);
        setContentView(R.layout.mapbox_starter);

        replaceFragment(R.id.mapbox_activity_fl, new MapFragment(), MAPBOX_FRAGMENT_TAG);
    }

    @Override
    protected void inject(ApplicationComponent component) {

    }
}
