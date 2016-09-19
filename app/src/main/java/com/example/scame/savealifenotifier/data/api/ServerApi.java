package com.example.scame.savealifenotifier.data.api;

import com.example.scame.savealifenotifier.data.entities.DestinationEntity;
import com.example.scame.savealifenotifier.data.entities.HelpMessageEntity;
import com.example.scame.savealifenotifier.data.entities.LocationMessageEntity;
import com.example.scame.savealifenotifier.data.entities.RegistrationEntity;
import com.example.scame.savealifenotifier.data.entities.StatusEntity;
import com.example.scame.savealifenotifier.data.entities.TokenUpdateEntity;

import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import rx.Observable;

public interface ServerApi {

    @PUT("http://10.0.1.6:8080/rest/user")
    Observable<ResponseBody> tokenUpdateRequest(@Body TokenUpdateEntity tokenEntity);

    @POST("http://10.0.1.6:8080/rest/user")
    Observable<ResponseBody> sendLocationToServer(@Body LocationMessageEntity locationEntity,
                                                  @Query("role") String role);

    @POST("http://10.0.1.6:8080/rest/user")
    Observable<ResponseBody> sendHelpMessage(@Body HelpMessageEntity helpEntity,
                                             @Query("role") String role);

    @PUT("http://10.0.1.6:8080/rest/user")
    Observable<ResponseBody> changeStatus(@Body StatusEntity statusEntity,
                                          @Query("role") String role);

    @POST("http://10.0.1.6:8080/rest/user")
    Observable<ResponseBody> sendDestination(@Body DestinationEntity destinationEntity,
                                             @Query("role") String role);

    @POST("http://10.0.1.6:8080/rest/user")
    Observable<ResponseBody> sendRegistrationRequest(@Body RegistrationEntity registrationEntity,
                                                   @Query("role") String role);
}
