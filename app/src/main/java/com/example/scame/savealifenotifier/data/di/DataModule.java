package com.example.scame.savealifenotifier.data.di;

import com.example.scame.savealifenotifier.data.repository.DirectionsDataManagerImp;
import com.example.scame.savealifenotifier.data.repository.FirebaseTokenManagerImp;
import com.example.scame.savealifenotifier.data.repository.GeocodingDataManagerImp;
import com.example.scame.savealifenotifier.data.repository.IDirectionsDataManager;
import com.example.scame.savealifenotifier.data.repository.IFirebaseTokenManager;
import com.example.scame.savealifenotifier.data.repository.IGeocodingDataManager;
import com.example.scame.savealifenotifier.data.repository.ILocationDataManager;
import com.example.scame.savealifenotifier.data.repository.IMessagesDataManager;
import com.example.scame.savealifenotifier.data.repository.IUserDataManager;
import com.example.scame.savealifenotifier.data.repository.LocationDataManagerImp;
import com.example.scame.savealifenotifier.data.repository.MessagesDataManagerImp;
import com.example.scame.savealifenotifier.data.repository.UserDataManagerImp;
import com.example.scame.savealifenotifier.domain.schedulers.ObserveOn;
import com.example.scame.savealifenotifier.domain.schedulers.SubscribeOn;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class DataModule {

    @Singleton
    @Provides
    OkHttpClient provideOkHttp() {
        return new OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)
                .build();
    }

    @Singleton
    @Provides
    Retrofit provideRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    IMessagesDataManager provideMessagesDataManager() {
        return new MessagesDataManagerImp();
    }

    @Singleton
    @Provides
    IGeocodingDataManager provideGeocodingDataManager() {
        return new GeocodingDataManagerImp();
    }

    @Singleton
    @Provides
    IDirectionsDataManager provideDirectionsDatamanager() {
        return new DirectionsDataManagerImp();
    }

    @Singleton
    @Provides
    ILocationDataManager provideLocationDataManager(SubscribeOn subscribeOn, ObserveOn observeOn) {
        return new LocationDataManagerImp(observeOn, subscribeOn);
    }

    @Singleton
    @Provides
    IFirebaseTokenManager provideFirebaseTokenMnager() {
        return new FirebaseTokenManagerImp();
    }

    @Singleton
    @Provides
    IUserDataManager provideUserDataManager() {
        return new UserDataManagerImp();
    }
}