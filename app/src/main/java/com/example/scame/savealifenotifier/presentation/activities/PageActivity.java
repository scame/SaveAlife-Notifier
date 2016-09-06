package com.example.scame.savealifenotifier.presentation.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.scame.savealifenotifier.R;
import com.example.scame.savealifenotifier.presentation.di.components.ApplicationComponent;
import com.example.scame.savealifenotifier.presentation.di.components.EndPointComponent;
import com.example.scame.savealifenotifier.presentation.di.components.HelpMeComponent;
import com.example.scame.savealifenotifier.presentation.di.modules.EndPointModule;
import com.example.scame.savealifenotifier.presentation.di.modules.HelpMeModule;
import com.example.scame.savealifenotifier.presentation.fragments.EndPointFragment;
import com.example.scame.savealifenotifier.presentation.fragments.HelpMeFragment;
import com.hanks.htextview.HTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PageActivity extends BaseActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;

    @BindView(R.id.text1) HTextView currentPageView;
    @BindView(R.id.text2) HTextView nextPageView;

    @BindView(R.id.arrows) ImageView arrows;

    private static final String CLICK_VALUE = "CLICK_VALUE";

    private static final String HELP_ME_FRAG_TAG = "helpMeTag";
    private static final String END_POINT_FRAG_TAG = "endPointTag";

    private HelpMeComponent helpMeComponent;
    private EndPointComponent endPointComponent;

    private String helpMeText = "HelpMe";
    private String mapText = "Map";

    private boolean clicked = true;

    private Animation animRotate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_activity);

        inject(getAppComponent());

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        animRotate = AnimationUtils.loadAnimation(this, R.anim.rotate);

        if (getSupportFragmentManager().findFragmentByTag(END_POINT_FRAG_TAG) == null) {
            replaceFragment(R.id.page_activity_fl, new HelpMeFragment(), HELP_ME_FRAG_TAG);
        }
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


    @OnClick(R.id.toolbar)
    public void onToolbarClick() {
        clicked = !clicked;

        arrows.startAnimation(animRotate);

        if (clicked) {
            currentPageView.animateText(helpMeText);
            nextPageView.animateText(mapText);
            replaceFragment(R.id.page_activity_fl, new HelpMeFragment(), HELP_ME_FRAG_TAG);
        } else {
            currentPageView.animateText(mapText);
            nextPageView.animateText(helpMeText);
            replaceFragment(R.id.page_activity_fl, new EndPointFragment(), END_POINT_FRAG_TAG);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean(CLICK_VALUE, clicked);

        super.onSaveInstanceState(savedInstanceState);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        clicked = savedInstanceState.getBoolean(CLICK_VALUE);

        if (savedInstanceState.getBoolean(CLICK_VALUE)) {
            currentPageView.animateText(helpMeText);
            nextPageView.animateText(mapText);
        } else {
            currentPageView.animateText(mapText);
            nextPageView.animateText(helpMeText);
        }
    }
}
