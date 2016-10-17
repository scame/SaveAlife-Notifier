package com.example.scame.savealifenotifier.presentation.di.components;


import android.content.Context;

import com.example.scame.savealifenotifier.FusedLocationService;
import com.example.scame.savealifenotifier.data.di.DataModule;
import com.example.scame.savealifenotifier.presentation.di.modules.ApplicationModule;
import com.example.scame.savealifenotifier.presentation.di.modules.EndPointModule;
import com.example.scame.savealifenotifier.presentation.di.modules.HelpMeModule;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessagingService;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {DataModule.class, ApplicationModule.class})
public interface ApplicationComponent {

    void inject(FirebaseMessagingService messagingService);

    void inject(FusedLocationService locationService);

    void inject(FirebaseInstanceIdService instanceIdService);

    Context getApp();

    HelpMeComponent getHelpMeComponent(HelpMeModule helpMeModule);

    EndPointComponent getEndPointComponent(EndPointModule endPointModule);
}
