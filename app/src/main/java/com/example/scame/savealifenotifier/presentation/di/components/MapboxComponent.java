package com.example.scame.savealifenotifier.presentation.di.components;


import com.example.scame.savealifenotifier.presentation.di.PerActivity;
import com.example.scame.savealifenotifier.presentation.di.modules.MapboxModule;
import com.example.scame.savealifenotifier.presentation.fragments.MapFragment;

import dagger.Subcomponent;

@PerActivity
@Subcomponent(modules = MapboxModule.class)
public interface MapboxComponent {

    void inject(MapFragment fragment);
}
