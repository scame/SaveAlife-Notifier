package com.example.scame.savealifenotifier.data.repository;


import com.example.scame.savealifenotifier.PrivateValues;
import com.example.scame.savealifenotifier.data.api.MapboxApi;
import com.example.scame.savealifenotifier.data.entities.LatLongPair;
import com.example.scame.savealifenotifier.data.mappers.DirectionModelMapper;
import com.example.scame.savealifenotifier.presentation.models.AddressModel;
import com.example.scame.savealifenotifier.presentation.models.DirectionModel;
import com.mapbox.services.commons.ServicesException;
import com.mapbox.services.commons.models.Position;
import com.mapbox.services.geocoding.v5.GeocodingCriteria;
import com.mapbox.services.geocoding.v5.MapboxGeocoding;
import com.mapbox.services.geocoding.v5.models.CarmenFeature;

import java.io.IOException;
import java.util.List;

import rx.Observable;

public class MapboxDataManagerImp implements IMapBoxDataManager {

    private static final String PROFILE_TYPE = "driving";

    private MapboxApi mapboxApi;

    private DirectionModelMapper directionModelMapper;

    private MapboxGeocoding mapboxGeocodingClient;

    public MapboxDataManagerImp(DirectionModelMapper directionModelMapper,
                                MapboxApi mapboxApi) {
        this.directionModelMapper = directionModelMapper;
        this.mapboxApi = mapboxApi;
    }

    @Override
    public Observable<AddressModel> getHumanReadableAddress(Position position) {
        return Observable.defer(() -> Observable.just(retrieveFormattedAddress(position)));
    }

    private AddressModel retrieveFormattedAddress(Position position) {
        AddressModel addressModel = new AddressModel();
        constructGeocodingClient(position);
        try {
            List<CarmenFeature> results = mapboxGeocodingClient.executeCall().body().getFeatures();
            if (results.size() > 0) {
                String placeName = results.get(0).getPlaceName();
                addressModel.setFormattedAddress(placeName);
            } else {
                addressModel.setFormattedAddress("No results");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return addressModel;
    }

    // TODO: move into module
    private void constructGeocodingClient(Position position) {
        try {
            mapboxGeocodingClient = new MapboxGeocoding.Builder()
                    .setAccessToken(PrivateValues.MAPBOX_KEY)
                    .setGeocodingType(GeocodingCriteria.TYPE_ADDRESS)
                    .setGeocodingTypes(new String [] { GeocodingCriteria.TYPE_POI, GeocodingCriteria.TYPE_ADDRESS,
                            GeocodingCriteria.TYPE_NEIGHBORHOOD, GeocodingCriteria.TYPE_PLACE})
                    .setCountries(new String [] {"ua", "us", "gb", "fr", "ru", "de"})
                    .setCoordinates(position)
                    .build();
        } catch (ServicesException e) {
            throw new RuntimeException(e.getCause());
        }
    }

    // TODO: implement through MapboxDirections
    @Override
    public Observable<DirectionModel> getDirection(LatLongPair origin, LatLongPair dest) {
        return mapboxApi.getDirection(PROFILE_TYPE, formatCoordinates(origin, dest), PrivateValues.MAPBOX_KEY)
                .map(directionModelMapper::convert);
    }

    private String formatCoordinates(LatLongPair origin, LatLongPair dest) {
        return origin.getLongitude() + "," + origin.getLatitude() + ";"
                + dest.getLongitude() + "," + dest.getLatitude();
    }
}
