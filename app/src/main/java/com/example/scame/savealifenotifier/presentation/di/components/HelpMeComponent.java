package com.example.scame.savealifenotifier.presentation.di.components;

import com.example.scame.savealifenotifier.presentation.di.PerActivity;
import com.example.scame.savealifenotifier.presentation.di.modules.HelpMeModule;
import com.example.scame.savealifenotifier.presentation.fragments.HelpMeFragment;

import dagger.Subcomponent;

@PerActivity
@Subcomponent(modules = HelpMeModule.class)
public interface HelpMeComponent {

    void inject(HelpMeFragment helpMeFragment);
}
