package com.example.scame.savealifenotifier.data.di;


import com.example.scame.savealifenotifier.data.mappers.DirectionModelMapper;
import com.example.scame.savealifenotifier.data.mappers.DriverMessageMapper;
import com.example.scame.savealifenotifier.data.mappers.HelpMessageMapper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MappersModule {

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
}
