package com.example.scame.savealifenotifier.data.api;

import com.example.scame.savealifenotifier.data.entities.DestinationEntity;
import com.example.scame.savealifenotifier.data.entities.HelpMessageEntity;
import com.example.scame.savealifenotifier.data.entities.LocationMessageEntity;
import com.example.scame.savealifenotifier.data.entities.StatusEntity;
import com.example.scame.savealifenotifier.data.entities.TokenUpdateEntity;

import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import rx.Observable;

public interface ServerApi {

    @PUT("http://10.0.1.255:8081/rest/user")
    Observable<ResponseBody> tokenUpdateRequest(@Body TokenUpdateEntity tokenEntity);

    @POST("http://10.0.1.255:8081/rest/send/")
    Observable<ResponseBody> sendLocationToServer(@Body LocationMessageEntity locationEntity);

    @POST("http://10.0.1.255:8081/rest/send/")
    Observable<ResponseBody> sendHelpMessage(@Body HelpMessageEntity helpEntity);

    @PUT("http://10.0.1.6:8081/rest/user")
    Observable<ResponseBody> changeStatus(@Body StatusEntity statusEntity);

    @POST("http://10.0.1.255:8081/rest/send/")
    Observable<ResponseBody> sendDestination(@Body DestinationEntity destinationEntity);
}
