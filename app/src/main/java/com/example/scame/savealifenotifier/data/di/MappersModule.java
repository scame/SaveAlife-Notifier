package com.example.scame.savealifenotifier.data.di;


import com.example.scame.savealifenotifier.data.mappers.AddressModelMapper;
import com.example.scame.savealifenotifier.data.mappers.DirectionModelMapper;
import com.example.scame.savealifenotifier.data.mappers.DriverMessageMapper;
import com.example.scame.savealifenotifier.data.mappers.HelpMessageMapper;
import com.example.scame.savealifenotifier.data.mappers.NewDirectionModelMapper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MappersModule {

    @Provides
    @Singleton
    AddressModelMapper provideAddressModelMapper() {
        return new AddressModelMapper();
    }

    @Provides
    @Singleton
    DirectionModelMapper provideDirectionModelMapper() {
        return new DirectionModelMapper();
    }

    @Provides
    @Singleton
    DriverMessageMapper provideDriverMessageMapper() {
        return new DriverMessageMapper();
    }

    @Provides
    @Singleton
    HelpMessageMapper provideHelpMessageMapper() {
        return new HelpMessageMapper();
    }

    @Provides
    @Singleton
    NewDirectionModelMapper provideNewDirectionMapper() {
        return new NewDirectionModelMapper();
    }
}
