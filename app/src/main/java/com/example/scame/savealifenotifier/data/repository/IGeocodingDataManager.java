package com.example.scame.savealifenotifier.data.repository;


import com.example.scame.savealifenotifier.presentation.models.AddressModel;

import rx.Observable;

public interface IGeocodingDataManager {

    Observable<AddressModel> getHumanReadableAddress(String latLng);
}
