package com.example.scame.savealifenotifier.presentation.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.scame.savealifenotifier.R;
import com.example.scame.savealifenotifier.presentation.di.HasComponent;
import com.example.scame.savealifenotifier.presentation.di.components.ApplicationComponent;
import com.example.scame.savealifenotifier.presentation.di.components.HelpMeComponent;
import com.example.scame.savealifenotifier.presentation.di.modules.HelpMeModule;
import com.example.scame.savealifenotifier.presentation.fragments.HelpMeFragment;

public class HelpMeActivity extends BaseActivity implements HasComponent<HelpMeComponent> {

    private static final String HELP_ME_FRAG_TAG = "helpMeTag";

    private HelpMeComponent helpMeComponent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_me_activity);


        inject(getAppComponent());
        replaceFragment(R.id.help_me_activity_fl, new HelpMeFragment(), HELP_ME_FRAG_TAG);
    }

    @Override
    protected void inject(ApplicationComponent component) {
        helpMeComponent = component.getHelpMeComponent(new HelpMeModule());
        helpMeComponent.inject(this);
    }

    @Override
    public HelpMeComponent getComponent() {
        return helpMeComponent;
    }
}
