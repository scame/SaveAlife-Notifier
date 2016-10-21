package com.example.scame.savealifenotifier.data.api;


import com.example.scame.savealifenotifier.data.entities.mapbox.DirectionEntity;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface MapboxApi {

    @GET("https://api.mapbox.com/directions/v5/mapbox/{profile}/{coordinates}")
    Observable<DirectionEntity> getDirection(@Path("profile") String profile,
                                             @Path("coordinates") String coordinates,
                                             @Query("access_token") String accessToken);
}
