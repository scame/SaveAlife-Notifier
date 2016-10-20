package com.example.scame.savealifenotifier.presentation.fragments;


import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.IntegerRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.dd.morphingbutton.MorphingButton;
import com.dd.morphingbutton.impl.LinearProgressButton;
import com.example.scame.savealifenotifier.PrivateValues;
import com.example.scame.savealifenotifier.R;
import com.example.scame.savealifenotifier.presentation.utility.ProgressGenerator;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.github.javiersantos.materialstyleddialogs.enums.Style;
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
import butterknife.OnClick;

public class MapFragment extends Fragment {

    public static final int DRIVER_MODE = 0;
    public static final int AMBULANCE_MODE = 1;
    public static final int NON_DRIVER_MODE = 2;

    private static final String MODE_STATE = "MODE";

    private boolean morphButtonClicked;

    private LatLng destination;

    private LatLng currentPosition;

    @BindView(R.id.mapbox_autocomplete)
    GeocoderAutoCompleteView autocompleteView;

    @BindView(R.id.mapbox_mapview)
    MapView mapboxView;

    @BindView(R.id.morph_btn)
    LinearProgressButton morphButton;

    @BindView(R.id.rb_ambulance)
    RadioButton ambulanceMode;
    @BindView(R.id.rb_driver)
    RadioButton driverMode;

    @BindView(R.id.rg_mode)
    RadioGroup modeToggle;

    private MapboxMap mapboxMap;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MapboxAccountManager.start(getContext(), PrivateValues.MAPBOX_KEY);
        View fragmentView = inflater.inflate(R.layout.map_fragment, container, false);

        ButterKnife.bind(this, fragmentView);

        setupMap(savedInstanceState);
        setupAutocomplete();

        restoreRadioGroup(savedInstanceState);
        morphToReady(morphButton, 0);

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
            destination = new LatLng(position.getLatitude(), position.getLongitude());
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

    @OnClick(R.id.my_location_fab)
    public void onLocationFabClick() {
        if (currentPosition != null & mapboxMap != null) {
            CameraPosition cameraPosition = new CameraPosition.Builder().target(currentPosition).zoom(14).build();
            mapboxMap.animateCamera((CameraUpdateFactory.newCameraPosition(cameraPosition)));
        }
    }

    public void showHumanReadableAddress(String address) {
        MaterialStyledDialog dialog = constructDialog(address);
        dialog.show();
    }

    private MaterialStyledDialog constructDialog(String description) {
        return new MaterialStyledDialog(getContext())
                .withDialogAnimation(true)
                .setTitle(getString(R.string.dialog_title))
                .setStyle(Style.HEADER_WITH_TITLE)
                .setHeaderColor(R.color.colorPrimaryDark)
                .setDescription(description)
                .setPositive(getString(R.string.dialog_positive), (dialog, which) -> {
                    simulateProgress(morphButton);

                    if (driverMode.isChecked()) {
                //        presenter.setupUserMode(DRIVER_MODE);
                    } else {
                //        presenter.setupUserMode(AMBULANCE_MODE);
                    }

                    // send current/destination coordinates & driver/ambulance status
                //    presenter.setupDestination(new LatLongPair(destination.latitude, destination.longitude));
                    // and start sending ongoing location updates
                })
                .setNegative(getString(R.string.dialog_negative), (dialog, which) -> {
                    morphButtonClicked = !morphButtonClicked;
                    dialog.dismiss();
                })
                .setCancelable(false)
                .build();
    }

    private void showConfirmDialog() {
        if (destination != null) {
            //presenter.geocodeToHumanReadableFormat(destination.getLatitude() + "," + destination.getLongitude());
            showHumanReadableAddress("Sechenova street");
        } else {
            buildNoDestinationSelectedSnackbar();
        }
    }

    private void buildNoDestinationSelectedSnackbar() {
        CoordinatorLayout coordinatorLayout = ButterKnife.findById(getActivity(), R.id.map_container);
        Snackbar.make(coordinatorLayout, "Please, choose the destination", Snackbar.LENGTH_LONG).show();
    }

    @OnClick(R.id.morph_btn)
    public void confirmButtonClick() {
        morphButtonClicked = !morphButtonClicked;

        if (morphButtonClicked) {
            showConfirmDialog();
        } else {
            // this is required by a server, so it knows that there's no point in sending
            // messages that ask to change a route
//            presenter.setupUserMode(NON_DRIVER_MODE);
//            presenter.changeDeviceStatus();
            morphToReady(morphButton, integer(R.integer.mb_animation));
        }
    }

    private void restoreRadioGroup(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            ambulanceMode.setChecked(savedInstanceState.getBoolean(MODE_STATE));
            driverMode.setChecked(!savedInstanceState.getBoolean(MODE_STATE));
        }
    }

    private void morphToReady(final MorphingButton btnMorph, int duration) {
        MorphingButton.Params square = MorphingButton.Params.create()
                .text(getString(R.string.mb_button))
                .duration(duration)
                .cornerRadius(dimen(R.dimen.mb_corner_radius_2))
                .width(dimen(R.dimen.mb_width_100))
                .height(dimen(R.dimen.mb_height_56))
                .color(color(R.color.colorAccent))
                .colorPressed(color(R.color.colorButtonPressed));

        btnMorph.morph(square);
    }

    private void morphToSuccess(final MorphingButton btnMorph) {
        MorphingButton.Params circle = MorphingButton.Params.create()
                .text(getString(R.string.mb_success))
                .duration(integer(R.integer.mb_animation))
                .cornerRadius(dimen(R.dimen.mb_height_56))
                .width(dimen(R.dimen.mb_width_120))
                .height(dimen(R.dimen.mb_height_56))
                .color(color(R.color.colorAccent))
                .colorPressed(color(R.color.colorButtonPressed))
                .icon(R.drawable.ic_done);

        btnMorph.morph(circle);
    }

    private void simulateProgress(@NonNull final LinearProgressButton button) {
        int progressColor = color(R.color.colorAccent);
        int color = color(R.color.mb_gray);
        int progressCornerRadius = dimen(R.dimen.mb_corner_radius_4);
        int width = dimen(R.dimen.mb_width_200);
        int height = dimen(R.dimen.mb_height_8);
        int duration = integer(R.integer.mb_animation);

        ProgressGenerator generator = new ProgressGenerator(() -> {
            morphToSuccess(button);
            button.unblockTouch();
        });

        button.blockTouch();
        button.morphToProgress(color, progressColor, progressCornerRadius, width, height, duration);
        generator.start(button);
    }

    private int dimen(@DimenRes int resId) {
        return (int) getResources().getDimension(resId);
    }

    private int color(@ColorRes int resId) {
        return getResources().getColor(resId);
    }

    private int integer(@IntegerRes int resId) {
        return getResources().getInteger(resId);
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
