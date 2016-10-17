package com.example.scame.savealifenotifier.data.mappers;

import com.example.scame.savealifenotifier.data.entities.GeocodingEntity;

public class AddressModelMapper {

    public com.example.scame.savealifenotifier.presentation.models.AddressModel convert(GeocodingEntity geocodingEntity) {

        com.example.scame.savealifenotifier.presentation.models.AddressModel addressModel = new com.example.scame.savealifenotifier.presentation.models.AddressModel();
        String formattedAddress = geocodingEntity.getResults().get(0).getFormattedAddress();
        addressModel.setFormattedAddress(formattedAddress);

        return addressModel;
    }
}