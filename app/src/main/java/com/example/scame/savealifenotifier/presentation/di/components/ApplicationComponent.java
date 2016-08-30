package com.example.scame.savealifenotifier.presentation.di.components;


import android.app.Application;

import com.example.scame.savealifenotifier.FusedLocationService;
import com.example.scame.savealifenotifier.data.di.DataModule;
import com.example.scame.savealifenotifier.presentation.di.modules.ApplicationModule;
import com.example.scame.savealifenotifier.presentation.di.modules.EndPointModule;
import com.example.scame.savealifenotifier.presentation.di.modules.HelpMeModule;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

@Singleton
@Component(modules = {DataModule.class, ApplicationModule.class})
public interface ApplicationComponent {

    void inject(FusedLocationService locationService);

    Application getApp();

    Retrofit getRetrofit();

    HelpMeComponent getHelpMeComponent(HelpMeModule helpMeModule);

    EndPointComponent getEndPointComponent(EndPointModule endPointModule);
}
