package com.example.scame.savealifenotifier.presentation.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.scame.savealifenotifier.R;
import com.example.scame.savealifenotifier.presentation.utility.Path;
import com.github.jorgecastillo.FillableLoader;
import com.github.jorgecastillo.State;
import com.github.jorgecastillo.listener.OnStateChangeListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashScreenActivity extends AppCompatActivity implements OnStateChangeListener {

    @BindView(R.id.logo) FillableLoader fillableLoaderLogo;
    @BindView(R.id.text) FillableLoader fillableLoaderText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        ButterKnife.bind(this);

        fillableLoaderText.setSvgPath(Path.LOGO_TEXT);
        fillableLoaderLogo.setSvgPath(Path.LOGO);
        fillableLoaderLogo.setOnStateChangeListener(this);
        fillableLoaderLogo.start();
    }

    @Override
    public void onStateChange(int state) {

        switch (state) {
            case State.STROKE_STARTED:
                fillableLoaderText.start();
                break;
            case State.FINISHED:
                startActivity(new Intent(this, PageActivity.class));
                break;
        }
    }
}
