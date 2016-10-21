package com.example.scame.savealifenotifier.data.mappers;


import android.graphics.Color;

import com.example.scame.savealifenotifier.data.entities.mapbox.DirectionEntity;
import com.example.scame.savealifenotifier.presentation.models.DirectionModel;
import com.mapbox.mapboxsdk.annotations.PolylineOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.services.commons.models.Position;
import com.mapbox.services.commons.utils.PolylineUtils;

import java.util.ArrayList;
import java.util.List;

public class DirectionModelMapper {

    private static final int FIRST_ROUTE = 0;

    private static final int PRECISION = 5;

    public DirectionModel convert(DirectionEntity directionEntity) {
        String encodedPolyline = directionEntity.getRoutes().get(FIRST_ROUTE).getGeometry();
        List<Position> positionList = PolylineUtils.decode(encodedPolyline, PRECISION);

        DirectionModel directionModel = new DirectionModel();
        directionModel.setPoints(convertToLatLngList(positionList));
        directionModel.setPolyline(getPolyline(positionList));

        return directionModel;
    }

    private PolylineOptions getPolyline(List<Position> pointList) {
        return new PolylineOptions()
                .addAll(convertToLatLngList(pointList))
                .color(Color.RED)
                .width((float) 3);
    }

    private List<LatLng> convertToLatLngList(List<Position> positions) {
        List<LatLng> latLngList = new ArrayList<>();

        for (Position position : positions) {
            latLngList.add(new LatLng(position.getLatitude(), position.getLongitude()));
        }
        return latLngList;
    }
}
