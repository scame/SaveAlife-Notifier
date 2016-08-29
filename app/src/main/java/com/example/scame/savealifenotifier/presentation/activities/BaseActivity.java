package com.example.scame.savealifenotifier.presentation.activities;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.example.scame.savealifenotifier.SaveAlifeApp;
import com.example.scame.savealifenotifier.presentation.di.components.ApplicationComponent;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        inject(getAppComponent());
    }

    protected void replaceFragment(int containerViewId, Fragment fragment, String TAG) {

        if (getSupportFragmentManager().findFragmentByTag(TAG) == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(containerViewId, fragment, TAG)
                    .commit();
        }
    }

    protected abstract void inject(ApplicationComponent component);

    protected ApplicationComponent getAppComponent() {
        return SaveAlifeApp.getAppComponent();
    }
}