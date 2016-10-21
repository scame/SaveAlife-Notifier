package com.example.scame.savealifenotifier.data.di;

import com.example.scame.savealifenotifier.data.api.DirectionsApi;
import com.example.scame.savealifenotifier.data.api.GeocodingApi;
import com.example.scame.savealifenotifier.data.api.MapboxApi;
import com.example.scame.savealifenotifier.data.api.ServerApi;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkingModule {

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
    DirectionsApi provideDirectionsApi(Retrofit retrofit) {
        return retrofit.create(DirectionsApi.class);
    }

    @Singleton
    @Provides
    GeocodingApi provideGeocodingApi(Retrofit retrofit) {
        return retrofit.create(GeocodingApi.class);
    }

    @Singleton
    @Provides
    ServerApi provideServerApi(Retrofit retrofit) {
        return retrofit.create(ServerApi.class);
    }

    @Singleton
    @Provides
    MapboxApi provideMapboxApi(Retrofit retrofit) {
        return retrofit.create(MapboxApi.class);
    }
}
