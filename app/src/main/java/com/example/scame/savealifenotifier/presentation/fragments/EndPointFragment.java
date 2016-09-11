package com.example.scame.savealifenotifier.presentation.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.IntegerRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dd.morphingbutton.MorphingButton;
import com.dd.morphingbutton.impl.LinearProgressButton;
import com.example.scame.savealifenotifier.R;
import com.example.scame.savealifenotifier.data.entities.LatLongPair;
import com.example.scame.savealifenotifier.presentation.activities.PageActivity;
import com.example.scame.savealifenotifier.presentation.presenters.IEndPointPresenter;
import com.example.scame.savealifenotifier.presentation.utility.ProgressGenerator;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.github.javiersantos.materialstyleddialogs.enums.Style;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EndPointFragment  extends BaseFragment implements OnMapReadyCallback,
                                            IEndPointPresenter.EndPointView {

    private static final int CIRCLE_RADIUS = 250;

    private static View fragmentView;

    @BindView(R.id.morph_btn) LinearProgressButton morphButton;
    @BindView(R.id.mapview) MapView mapView;

    private boolean morphButtonClicked;

    private GoogleMap googleMap;

    private LatLng destination;
    private LatLng currentPosition;

    private Marker currentPositionMarker;
    private Circle currentPositionCircle;
    private Polyline currentPolyline;

    private Marker destinationMarker;

    private Bundle savedInstanceState;

    @Inject
    IEndPointPresenter<IEndPointPresenter.EndPointView> presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.savedInstanceState = savedInstanceState;

        if (fragmentView != null) {
            ViewGroup parent = (ViewGroup) fragmentView.getParent();
            if (parent != null)
                parent.removeView(fragmentView);
        }

        try {
            fragmentView = inflater.inflate(R.layout.end_point_fragment, container, false);
        } catch (InflateException e) {
            //* map is already there, just return view as it is *//
        }

        ButterKnife.bind(this, fragmentView);

        ((PageActivity) getActivity()).getEndPointComponent().inject(this);

        configureMapView(savedInstanceState);
        configureAutocomplete();

        presenter.setView(this);

        morphToReady(morphButton, 0);

        return fragmentView;
    }

    private void configureAutocomplete() {
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getActivity().getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                updateDestinationPoint(place.getLatLng());
            }

            @Override
            public void onError(Status status) {
                Log.i("LOG_TAG", "An error occurred: " + status);
            }
        });
    }

    private void configureMapView(Bundle savedInstanceState) {
        Bundle mapViewSavedInstanceState = savedInstanceState != null ?
                savedInstanceState.getBundle("mapViewSaveState") : null;
        mapView.onCreate(mapViewSavedInstanceState);
        mapView.getMapAsync(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        mapView.onResume();
        presenter.startLocationUpdates();
    }

    @Override
    public void onPause() {
        super.onPause();

        mapView.onPause();
        presenter.pause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();

        mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mapView.onDestroy();
    }


    @Override
    @SuppressWarnings({"MissingPermission"})
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        googleMap.setOnMapLongClickListener(this::updateDestinationPoint);

        if (savedInstanceState != null) {
            List<LatLng> pathList = savedInstanceState.getParcelableArrayList(getString(R.string.path_key));
            LatLng currentLocation = savedInstanceState.getParcelable(getString(R.string.current_location_key));
            LatLng destination = savedInstanceState.getParcelable(getString(R.string.destination_key));

            if (pathList != null) {
                currentPolyline = googleMap.addPolyline(new PolylineOptions().addAll(pathList));
            }
            if (currentLocation != null) {
                updateCurrentLocation(new LatLongPair(currentLocation.latitude, currentLocation.longitude));
            }
            if (destination != null) {
                destinationMarker = googleMap.addMarker(new MarkerOptions().position(destination));
                this.destination = destination;
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Bundle mapViewSaveState = new Bundle(outState);
        mapView.onSaveInstanceState(mapViewSaveState);
        outState.putBundle("mapViewSaveState", mapViewSaveState);

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

        super.onSaveInstanceState(outState);
    }

    @Override
    public void updateCurrentLocation(LatLongPair latLongPair) {
        if (googleMap != null) {

            if (currentPositionMarker != null) {
                currentPositionMarker.remove();
                currentPositionCircle.remove();
            }

            currentPosition = new LatLng(latLongPair.getLatitude(), latLongPair.getLongitude());
            currentPositionMarker = googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(latLongPair.getLatitude(), latLongPair.getLongitude())));
            currentPositionCircle = googleMap.addCircle(new CircleOptions()
                    .fillColor(R.color.colorPrimary)
                    .strokeColor(R.color.colorPrimaryDark)
                    .center(new LatLng(latLongPair.getLatitude(), latLongPair.getLongitude()))
                    .radius(CIRCLE_RADIUS));

            if (destination != null) {
                presenter.computeDirection(
                        new LatLongPair(currentPosition.latitude, currentPosition.longitude),
                        new LatLongPair(destination.latitude, destination.longitude)
                );
            }
        }
    }

    private void updateDestinationPoint(LatLng latLng) {
        this.destination = latLng;

        if (destinationMarker != null) {
            destinationMarker.remove();
        }

        destinationMarker = googleMap.addMarker(new MarkerOptions().position(latLng));

        presenter.computeDirection(new LatLongPair(currentPosition.latitude, currentPosition.longitude),
                new LatLongPair(destination.latitude, destination.longitude));
    }

    @OnClick(R.id.my_location_fab)
    public void onLocationFabClick() {
        if (currentPosition != null & googleMap != null) {
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, 14));
        }
    }

    @OnClick(R.id.morph_btn)
    public void confirmButtonClick(){
        morphButtonClicked = !morphButtonClicked;

        if (morphButtonClicked) {
            showConfirmDialog();
        } else {
            // TODO: implement success button logic
            morphToReady(morphButton, integer(R.integer.mb_animation));
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
                .colorPressed(color(R.color.colorAccent));

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
                .colorPressed(color(R.color.colorAccent))
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

    private MaterialStyledDialog destinationDialog(String description) {
        return new MaterialStyledDialog(getContext())
                .withDialogAnimation(true)
                .setTitle(getString(R.string.dialog_title))
                .setStyle(Style.HEADER_WITH_TITLE)
                .setHeaderColor(R.color.colorPrimaryDark)
                .setDescription(description)
                .setPositive(getString(R.string.dialog_positive), (dialog, which) -> {
                    simulateProgress(morphButton);
                    presenter.setupDestination(new LatLongPair(destination.latitude, destination.longitude));
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
            presenter.geocodeToHumanReadableFormat(destination.latitude + "," + destination.longitude);
        } else {
            buildNoDestinationSelectedSnackbar();
        }
    }

    private void buildNoDestinationSelectedSnackbar() {
        CoordinatorLayout coordinatorLayout = ButterKnife.findById(getActivity(), R.id.map_container);
        Snackbar.make(coordinatorLayout, "Please, choose the destination", Snackbar.LENGTH_LONG).show();
    }

    // presenter's callback
    @Override
    public void showHumanReadableAddress(String address) {
        MaterialStyledDialog dialog = destinationDialog(address);
        dialog.show();
    }

    @Override
    public void drawDirectionPolyline(PolylineOptions polyline) {
        if (currentPolyline != null) {
            currentPolyline.remove();
        }

        currentPolyline = googleMap.addPolyline(polyline.color(Color.BLACK));
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
}
