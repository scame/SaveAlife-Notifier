package com.example.scame.savealifenotifier.data.di;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.scame.savealifenotifier.data.api.DirectionsApi;
import com.example.scame.savealifenotifier.data.api.GeocodingApi;
import com.example.scame.savealifenotifier.data.api.MapboxApi;
import com.example.scame.savealifenotifier.data.api.ServerApi;
import com.example.scame.savealifenotifier.data.mappers.AddressModelMapper;
import com.example.scame.savealifenotifier.data.mappers.DirectionModelMapper;
import com.example.scame.savealifenotifier.data.mappers.NewDirectionModelMapper;
import com.example.scame.savealifenotifier.data.repository.DirectionsDataManagerImp;
import com.example.scame.savealifenotifier.data.repository.FirebaseTokenManagerImp;
import com.example.scame.savealifenotifier.data.repository.GeocodingDataManagerImp;
import com.example.scame.savealifenotifier.data.repository.IDirectionsDataManager;
import com.example.scame.savealifenotifier.data.repository.IFirebaseTokenManager;
import com.example.scame.savealifenotifier.data.repository.IGeocodingDataManager;
import com.example.scame.savealifenotifier.data.repository.ILocationDataManager;
import com.example.scame.savealifenotifier.data.repository.IMapBoxDataManager;
import com.example.scame.savealifenotifier.data.repository.IMessagesDataManager;
import com.example.scame.savealifenotifier.data.repository.IUserDataManager;
import com.example.scame.savealifenotifier.data.repository.LocationDataManagerImp;
import com.example.scame.savealifenotifier.data.repository.MapboxDataManagerImp;
import com.example.scame.savealifenotifier.data.repository.MessagesDataManagerImp;
import com.example.scame.savealifenotifier.data.repository.UserDataManagerImp;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DataManagersModule {

    @Singleton
    @Provides
    IMessagesDataManager provideMessagesDataManager(ILocationDataManager locationDataManager,
                                                    IFirebaseTokenManager firebaseTokenManager,
                                                    IUserDataManager userDataManager,
                                                    ServerApi serverApi, Context context) {
        return new MessagesDataManagerImp(locationDataManager, firebaseTokenManager, userDataManager,
                serverApi, context);
    }

    @Singleton
    @Provides
    IGeocodingDataManager provideGeocodingDataManager(AddressModelMapper addressModelMapper,
                                                      GeocodingApi geocodingApi) {
        return new GeocodingDataManagerImp(addressModelMapper, geocodingApi);
    }

    @Singleton
    @Provides
    IDirectionsDataManager provideDirectionsDatamanager(DirectionsApi directionsApi,
                                                        DirectionModelMapper mapper) {
        return new DirectionsDataManagerImp(directionsApi, mapper);
    }

    @Singleton
    @Provides
    ILocationDataManager provideLocationDataManager(SharedPreferences sharedPrefs, Context context) {
        return new LocationDataManagerImp(sharedPrefs, context);
    }

    @Singleton
    @Provides
    IFirebaseTokenManager provideFirebaseTokenMnager(SharedPreferences sharedPrefs, Context context) {
        return new FirebaseTokenManagerImp(sharedPrefs, context);
    }

    @Singleton
    @Provides
    IUserDataManager provideUserDataManager(SharedPreferences sharedPrefs, Context context) {
        return new UserDataManagerImp(sharedPrefs, context);
    }

    @Singleton
    @Provides
    IMapBoxDataManager provideMapboxDataManager(NewDirectionModelMapper directionModelMapper,
                                                MapboxApi mapboxApi) {
        return new MapboxDataManagerImp(directionModelMapper, mapboxApi);
    }
}
