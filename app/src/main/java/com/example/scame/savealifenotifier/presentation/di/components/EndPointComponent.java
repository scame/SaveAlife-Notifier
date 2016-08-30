package com.example.scame.savealifenotifier.presentation.di.components;

import com.example.scame.savealifenotifier.presentation.di.PerActivity;
import com.example.scame.savealifenotifier.presentation.di.modules.EndPointModule;
import com.example.scame.savealifenotifier.presentation.fragments.EndPointFragment;

import dagger.Subcomponent;

@PerActivity
@Subcomponent(modules = EndPointModule.class)
public interface EndPointComponent {

    void inject(EndPointFragment fragment);
}
