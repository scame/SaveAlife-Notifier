package com.example.scame.savealifenotifier.data.repository;


import com.example.scame.savealifenotifier.data.entities.LatLongPair;
import com.example.scame.savealifenotifier.presentation.models.AddressModel;
import com.example.scame.savealifenotifier.presentation.models.NewDirectionModel;
import com.mapbox.services.commons.models.Position;

import rx.Observable;

public interface IMapBoxDataManager {

    Observable<AddressModel> getHumanReadableAddress(Position position);

    Observable<NewDirectionModel> getDirection(LatLongPair origin, LatLongPair dest);
}
