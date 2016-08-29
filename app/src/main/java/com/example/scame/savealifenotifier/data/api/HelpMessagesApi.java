package com.example.scame.savealifenotifier.data.api;


import okhttp3.ResponseBody;
import retrofit2.http.POST;
import rx.Observable;

public interface HelpMessagesApi {

    @POST("http://10.0.1.57:8080/rest/send/")
    Observable<ResponseBody> sendHelpMessage(String message);
}
