package com.example.scame.savealifenotifier.presentation.di.components;


import android.app.Application;

import com.example.scame.savealifenotifier.data.di.DataModule;
import com.example.scame.savealifenotifier.presentation.di.modules.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

@Singleton
@Component(modules = {DataModule.class, ApplicationModule.class})
public interface ApplicationComponent {

    Application getApp();

    Retrofit getRetrofit();
}
