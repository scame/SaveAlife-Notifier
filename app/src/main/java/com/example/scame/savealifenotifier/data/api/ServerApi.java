package com.example.scame.savealifenotifier.data.api;

import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

public interface ServerApi {

    @POST("http://10.0.1.57:8080/rest/send/")
    Observable<ResponseBody> sendLocationToServer(@Body String location);
}
