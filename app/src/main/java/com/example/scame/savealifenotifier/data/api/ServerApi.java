package com.example.scame.savealifenotifier.data.api;

import com.example.scame.savealifenotifier.data.entities.backend.DestinationEntity;
import com.example.scame.savealifenotifier.data.entities.backend.HelpMessageEntity;
import com.example.scame.savealifenotifier.data.entities.backend.LocationMessageEntity;
import com.example.scame.savealifenotifier.data.entities.backend.RegistrationEntity;
import com.example.scame.savealifenotifier.data.entities.backend.StatusEntity;
import com.example.scame.savealifenotifier.data.entities.backend.TokenUpdateEntity;

import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import rx.Observable;

public interface ServerApi {

    @PUT("http://37.139.22.28:8080/savelife/rest/user")
    Observable<ResponseBody> tokenUpdateRequest(@Body TokenUpdateEntity tokenUpdateEntity);

    @POST("http://37.139.22.28:8080/savelife/rest/user")
    Observable<ResponseBody> sendLocationToServer(@Body LocationMessageEntity locationEntity,
                                                  @Query("role") String role);

    @POST("http://37.139.22.28:8080/savelife/rest/user")
    Observable<ResponseBody> sendHelpMessage(@Body HelpMessageEntity helpEntity,
                                             @Query("role") String role);

    @PUT("http://37.139.22.28:8080/savelife/rest/user")
    Observable<ResponseBody> changeStatus(@Body StatusEntity statusEntity);

    @POST("http://37.139.22.28:8080/savelife/rest/user")
    Observable<ResponseBody> sendDestination(@Body DestinationEntity destinationEntity,
                                             @Query("role") String role);

    @POST("http://37.139.22.28:8080/savelife/rest/user")
    Observable<ResponseBody> sendRegistrationRequest(@Body RegistrationEntity registrationEntity,
                                                   @Query("role") String role);
}
