package com.example.scame.savealifenotifier.presentation.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.scame.savealifenotifier.PrivateValues;
import com.example.scame.savealifenotifier.R;
import com.mapbox.mapboxsdk.MapboxAccountManager;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.services.android.geocoder.ui.GeocoderAutoCompleteView;
import com.mapbox.services.commons.models.Position;
import com.mapbox.services.geocoding.v5.GeocodingCriteria;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MapFragment extends Fragment {

    @BindView(R.id.mapbox_autocomplete)
    GeocoderAutoCompleteView autocompleteView;

    @BindView(R.id.mapbox_mapview)
    MapView mapboxView;

    private MapboxMap mapboxMap;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MapboxAccountManager.start(getContext(), PrivateValues.MAPBOX_KEY);
        View fragmentView = inflater.inflate(R.layout.map_fragment, container, false);

        ButterKnife.bind(this, fragmentView);

        setupMap(savedInstanceState);
        setupAutocomplete();

        return fragmentView;
    }

    private void setupMap(Bundle savedInstanceState) {
        mapboxView.onCreate(savedInstanceState);
        mapboxView.getMapAsync(mapboxMap -> this.mapboxMap = mapboxMap);
    }

    private void setupAutocomplete() {
        autocompleteView.setAccessToken(PrivateValues.MAPBOX_KEY);
        autocompleteView.setType(GeocodingCriteria.TYPE_POI);
        autocompleteView.setHighlightColor(getResources().getColor(R.color.colorAccent));
        autocompleteView.setOnFeatureListener(feature -> {
            Position position = feature.asPosition();
            updateMap(position.getLatitude(), position.getLongitude());
        });
    }

    private void updateMap(double latitude, double longitude) {
        mapboxMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)));

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(latitude, longitude))
                .zoom(15)
                .build();
        mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
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
    public void onDestroy() {
        super.onDestroy();
        mapboxView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapboxView.onLowMemory();
    }
}
