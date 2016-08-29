package com.example.scame.savealifenotifier.presentation.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.scame.savealifenotifier.R;
import com.example.scame.savealifenotifier.presentation.di.components.ApplicationComponent;

public class HelpMeActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_me_activity);
    }

    @Override
    protected void inject(ApplicationComponent component) {

    }
}
