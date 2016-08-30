package com.example.scame.savealifenotifier.presentation.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;

import com.example.scame.savealifenotifier.R;
import com.example.scame.savealifenotifier.presentation.di.components.ApplicationComponent;
import com.example.scame.savealifenotifier.presentation.di.components.EndPointComponent;
import com.example.scame.savealifenotifier.presentation.di.components.HelpMeComponent;
import com.example.scame.savealifenotifier.presentation.di.modules.EndPointModule;
import com.example.scame.savealifenotifier.presentation.di.modules.HelpMeModule;
import com.example.scame.savealifenotifier.presentation.fragments.EndPointFragment;
import com.example.scame.savealifenotifier.presentation.fragments.HelpMeFragment;

public class PageActivity extends BaseActivity {

    private static final String HELP_ME_FRAG_TAG = "helpMeTag";
    private static final String END_POINT_FRAG_TAG = "endPointTag";

    private HelpMeComponent helpMeComponent;
    private EndPointComponent endPointComponent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_activity);

        inject(getAppComponent());
        replaceFragment(R.id.page_activity_fl, new HelpMeFragment(), HELP_ME_FRAG_TAG);
    }

    @Override
    protected void inject(ApplicationComponent component) {
        helpMeComponent = component.getHelpMeComponent(new HelpMeModule());
        endPointComponent = component.getEndPointComponent(new EndPointModule());
    }

    public HelpMeComponent getHelpMeComponent() {
        return helpMeComponent;
    }

    public EndPointComponent getEndPointComponent() {
        return endPointComponent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.switch_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.switch_tab_item) {
            replaceFragment(R.id.page_activity_fl, new EndPointFragment(), END_POINT_FRAG_TAG);
            replaceFragment(R.id.page_activity_fl, new HelpMeFragment(), HELP_ME_FRAG_TAG);
        }

        return super.onOptionsItemSelected(item);
    }
}
