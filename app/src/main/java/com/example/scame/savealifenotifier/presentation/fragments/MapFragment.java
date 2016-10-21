package com.example.scame.savealifenotifier.presentation.fragments;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.IntegerRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.dd.morphingbutton.MorphingButton;
import com.dd.morphingbutton.impl.LinearProgressButton;
import com.example.scame.savealifenotifier.PrivateValues;
import com.example.scame.savealifenotifier.R;
import com.example.scame.savealifenotifier.SaveAlifeApp;
import com.example.scame.savealifenotifier.data.entities.LatLongPair;
import com.example.scame.savealifenotifier.presentation.activities.PageActivity;
import com.example.scame.savealifenotifier.presentation.di.modules.MapboxModule;
import com.example.scame.savealifenotifier.presentation.presenters.IMapPresenter;
import com.example.scame.savealifenotifier.presentation.utility.ProgressGenerator;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.github.javiersantos.materialstyleddialogs.enums.Style;
import com.mapbox.mapboxsdk.MapboxAccountManager;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.annotations.Polyline;
import com.mapbox.mapboxsdk.annotations.PolylineOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.services.android.geocoder.ui.GeocoderAutoCompleteView;
import com.mapbox.services.commons.models.Position;
import com.mapbox.services.geocoding.v5.GeocodingCriteria;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MapFragment extends Fragment implements IMapPresenter.MapView {

    public static final int DRIVER_MODE = 0;
    public static final int AMBULANCE_MODE = 1;
    public static final int NON_DRIVER_MODE = 2;

    private static final String MODE_STATE = "MODE";

    private boolean morphButtonClicked;

    private Polyline currentPolyline;

    private LatLng destination;

    private LatLng currentPosition;

    private Marker currentPositionMarker;

    private Marker destinationMarker;

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

    @Inject
    IMapPresenter<IMapPresenter.MapView> presenter;

    private MapboxMap mapboxMap;

    private Bundle savedInstanceState;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MapboxAccountManager.start(getContext(), PrivateValues.MAPBOX_KEY);
        View fragmentView = inflater.inflate(R.layout.map_fragment, container, false);

        this.savedInstanceState = savedInstanceState;
        ButterKnife.bind(this, fragmentView);
        inject();
        presenter.setView(this);

        setupMap(savedInstanceState);
        setupAutocomplete();

        restoreRadioGroup(savedInstanceState);
        morphToReady(morphButton, 0);

        return fragmentView;
    }

    private void inject() {
        SaveAlifeApp.getAppComponent().getMapboxComponent(new MapboxModule()).inject(this);
        if (getActivity() instanceof PageActivity) {
            ((PageActivity) getActivity()).getMapboxComponent().inject(this);
        }
    }

    @Override
    public void drawDirectionPolyline(PolylineOptions polylineOptions) {
        if (currentPolyline != null) {
            currentPolyline.remove();
        }
        currentPolyline = mapboxMap.addPolyline(polylineOptions);
    }

    @Override
    public void updateCurrentLocation(LatLongPair latLongPair) {
        currentPosition = new LatLng(latLongPair.getLatitude(), latLongPair.getLongitude());

        if (mapboxMap != null) {
            if (currentPositionMarker != null) {
                currentPositionMarker.remove();
            }

            currentPosition = new LatLng(latLongPair.getLatitude(), latLongPair.getLongitude());
            currentPositionMarker = mapboxMap.addMarker(
                    new MarkerOptions().position(new LatLng(latLongPair.getLatitude(), latLongPair.getLongitude()))
            );
            currentPositionMarker.setIcon(getMarkerIcon());

            recomputeDirectionPolyline();
        }
    }

    // FIXME: 10/21/16 shouldn't recompute every time device' position changes
    private void recomputeDirectionPolyline() {
        if (destination != null && currentPosition != null) {
            presenter.computeDirection(
                    new LatLongPair(currentPosition.getLatitude(), currentPosition.getLongitude()),
                    new LatLongPair(destination.getLatitude(), destination.getLongitude())
            );
        }
    }

    private void updateDestinationPoint(LatLng latLng) {
        this.destination = latLng;

        if (destinationMarker != null) {
            destinationMarker.remove();
        }
        destinationMarker = mapboxMap.addMarker(new MarkerOptions().position(latLng));
        presenter.computeDirection(
                new LatLongPair(currentPosition.getLatitude(), currentPosition.getLongitude()),
                new LatLongPair(destination.getLatitude(), destination.getLongitude())
        );
    }

    private Icon getMarkerIcon() {
        IconFactory iconFactory = IconFactory.getInstance(getContext());
        Drawable iconDrawable = ContextCompat.getDrawable(getContext(), R.drawable.red_circle);
        return iconFactory.fromDrawable(iconDrawable);
    }

    private void setupMap(Bundle savedInstanceState) {
        mapboxView.onCreate(savedInstanceState);
        mapboxView.getMapAsync(this::initMap);
    }

    private void initMap(MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;

        mapboxMap.setOnMapLongClickListener(this::updateDestinationPoint);

        if (savedInstanceState != null) {
            List<LatLng> pathList = savedInstanceState.getParcelableArrayList(getString(R.string.path_key));
            LatLng currentLocation = savedInstanceState.getParcelable(getString(R.string.current_location_key));
            LatLng destination = savedInstanceState.getParcelable(getString(R.string.destination_key));

            if (pathList != null) {
                currentPolyline = mapboxMap.addPolyline(new PolylineOptions().addAll(pathList));
            }
            if (currentLocation != null) {
                updateCurrentLocation(new LatLongPair(currentLocation.getLatitude(), currentLocation.getLongitude()));
            }
            if (destination != null) {
                destinationMarker = mapboxMap.addMarker(new MarkerOptions().position(destination));
                this.destination = destination;
            }
        } else if (currentPosition != null) {
            updateCurrentLocation(new LatLongPair(currentPosition.getLatitude(), currentPosition.getLongitude()));
        }
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
                        presenter.setupUserMode(DRIVER_MODE);
                    } else {
                        presenter.setupUserMode(AMBULANCE_MODE);
                    }

                    // send current/destination coordinates & driver/ambulance status
                    presenter.setupDestination(new LatLongPair(destination.getLatitude(), destination.getLongitude()));
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
            Position position = Position.fromCoordinates(destination.getLongitude(), destination.getLatitude());
            presenter.geocodeToHumanReadableFormat(position);
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
            presenter.setupUserMode(NON_DRIVER_MODE);
            presenter.changeDeviceStatus();
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
        presenter.startLocationUpdates();
        mapboxView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.pause();
        mapboxView.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapboxView.onSaveInstanceState(outState);

        if (currentPolyline != null) {
            outState.putParcelableArrayList(getString(R.string.path_key),
                    new ArrayList<>(currentPolyline.getPoints()));
        }

        if (currentPosition != null) {
            outState.putParcelable(getString(R.string.current_location_key), currentPosition);
        }

        if (destination != null) {
            outState.putParcelable(getString(R.string.destination_key), destination);
        }

        outState.putBoolean(MODE_STATE, ambulanceMode.isChecked());
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
