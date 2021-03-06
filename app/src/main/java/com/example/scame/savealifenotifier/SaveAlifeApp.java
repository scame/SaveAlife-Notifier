package com.example.scame.savealifenotifier;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;

import com.example.scame.savealifenotifier.data.di.DataModule;
import com.example.scame.savealifenotifier.presentation.di.components.ApplicationComponent;
import com.example.scame.savealifenotifier.presentation.di.components.DaggerApplicationComponent;
import com.example.scame.savealifenotifier.presentation.di.modules.ApplicationModule;
import com.mapbox.mapboxsdk.MapboxAccountManager;

public class SaveAlifeApp extends Application {

    private static ApplicationComponent appComponent;

    public static SaveAlifeApp getApp(Context context) {
        return (SaveAlifeApp) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        buildAppComponent();
        MapboxAccountManager.start(this, PrivateValues.MAPBOX_KEY);
        startService(new Intent(this, FusedLocationService.class));
    }

    private void buildAppComponent() {
        appComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .dataModule(new DataModule())
                .build();
    }

    public static ApplicationComponent getAppComponent() {
        return appComponent;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        MultiDex.install(this);
    }
}
