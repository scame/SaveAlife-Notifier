package com.example.scame.savealifenotifier.data.api;

import com.example.scame.savealifenotifier.data.entities.ServerMessageEntity;

import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import rx.Observable;

public interface ServerApi {

    @POST("http://10.0.1.57:8080/rest/send/")
    Observable<ResponseBody> registrationRequest(@Body ServerMessageEntity messageEntity);

    @PUT("http://10.0.1.57:8080/rest/user")
    Observable<ResponseBody> tokenUpdateRequest(@Body ServerMessageEntity messageEntity);

    @POST("http://10.0.1.57:8080/rest/send/")
    Observable<ResponseBody> sendLocationToServer(@Body ServerMessageEntity messageEntity);

    @POST("http://10.0.1.57:8080/rest/send/")
    Observable<ResponseBody> sendHelpMessage(@Body ServerMessageEntity messageEntity);

    @PUT("http://10.0.1.57:8080/rest/user")
    Observable<ResponseBody> changeStatus(@Body ServerMessageEntity messageEntity);
}
